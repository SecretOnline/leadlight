import { resolve } from "node:path";
import { promisify } from "node:util";
import Sharp from "sharp";

const INPUT_DIR = resolve(import.meta.dirname, "../blockbench/mojang");
const OUTPUT_DIR = resolve(
  import.meta.dirname,
  "../common/src/main/resources/assets/leadlight/textures/item",
);

const COLORS = [
  "white",
  "orange",
  "magenta",
  "light_blue",
  "yellow",
  "lime",
  "pink",
  "gray",
  "light_gray",
  "cyan",
  "purple",
  "blue",
  "brown",
  "green",
  "red",
  "black",
];
const OFFSET = 4;
const WIDTH = 8;
const SOURCE_SIZE = 16;

for (const color of COLORS) {
  const instance = new Sharp(resolve(INPUT_DIR, `${color}_stained_glass.png`));
  const output = new Sharp({
    create: {
      width: 16,
      height: 16,
      channels: 4,
      background: { r: 0, g: 0, b: 0, alpha: 0 },
    },
  });

  const main = await instance
    .clone()
    .extract({
      left: 0,
      top: 0,
      width: WIDTH - 1,
      height: WIDTH - 1,
    })
    .toBuffer();
  const rightEdge = await instance
    .clone()
    .extract({
      left: SOURCE_SIZE - 1,
      top: 0,
      width: 1,
      height: WIDTH,
    })
    .toBuffer();
  const bottomEdge = await instance
    .clone()
    .extract({
      left: 0,
      top: SOURCE_SIZE - 1,
      width: WIDTH - 1,
      height: 1,
    })
    .toBuffer();

  output.composite([
    { input: main, left: OFFSET, top: OFFSET },
    { input: rightEdge, left: OFFSET + (WIDTH - 1), top: OFFSET },
    { input: bottomEdge, left: OFFSET, top: OFFSET + (WIDTH - 1) },
  ]);

  const filename = `${color}_cut_stained_glass_pane.png`;
  const outputPath = resolve(OUTPUT_DIR, filename);
  console.log(`Writing ${filename}`);
  await output.png().toFile(outputPath);
}
