import { readFile, writeFile } from "node:fs/promises";
import { resolve } from "node:path";

type Shape =
  | "large"
  | "vertical"
  | "horizontal"
  | "square"
  | "cross"
  | "diamond";
type Part = "post" | "side" | "side_alt" | "no_side" | "no_side_alt";

type SimpleModel = {
  textures: Record<string, string>;
  elements: { name: string }[];
  display?: unknown;
};

const INPUT_FILE = resolve(
  import.meta.dirname,
  "../blockbench/window_frame_all.json",
);
const OUTPUT_DIR = resolve(
  import.meta.dirname,
  "../common/src/main/resources/assets/leadlight/models/block",
);

const MAIN_PARTS: Part[] = ["post", "side", "side_alt"];

const SHAPE_PART_ELEMENTS: Record<Shape, Record<Part, string[]>> = {
  large: {
    post: ["center_ceil", "center_floor"],
    side: ["side_n", "bar_ceil_n", "bar_floor_n"],
    side_alt: ["side_s", "bar_ceil_s", "bar_floor_s"],
    no_side: ["no_side_tall_n"],
    no_side_alt: ["no_side_tall_s"],
  },
  vertical: {
    post: ["center_ceil", "center_floor"],
    side: ["side_n", "bar_ceil_n", "bar_floor_n"],
    side_alt: ["side_s", "bar_ceil_s", "bar_floor_s"],
    no_side: ["no_side_tall_n"],
    no_side_alt: ["no_side_tall_s"],
  },
  horizontal: {
    post: ["center_ceil", "center_center", "center_floor"],
    side: ["side_n", "bar_ceil_n", "bar_center_n", "bar_floor_n"],
    side_alt: ["side_s", "bar_ceil_s", "bar_center_s", "bar_floor_s"],
    no_side: ["no_side_top_n", "no_side_bottom_n"],
    no_side_alt: ["no_side_top_s", "no_side_bottom_s"],
  },
  square: {
    post: ["center_ceil", "center_center", "center_floor"],
    side: ["side_n", "bar_ceil_n", "bar_center_n", "bar_floor_n"],
    side_alt: ["side_s", "bar_ceil_s", "bar_center_s", "bar_floor_s"],
    no_side: ["no_side_top_n", "no_side_bottom_n"],
    no_side_alt: ["no_side_top_s", "no_side_bottom_s"],
  },
  cross: {
    post: ["center_ceil", "center_center", "center_floor"],
    side: [
      "side_n",
      "bar_ceil_n",
      "cross_top_n",
      "cross_bottom_n",
      "bar_floor_n",
    ],
    side_alt: [
      "side_s",
      "bar_ceil_s",
      "cross_top_s",
      "cross_bottom_s",
      "bar_floor_s",
    ],
    no_side: ["no_side_top_n", "no_side_bottom_n"],
    no_side_alt: ["no_side_top_s", "no_side_bottom_s"],
  },
  diamond: {
    post: ["center_ceil", "center_floor"],
    side: [
      "side_n",
      "bar_ceil_n",
      "diam_top_n",
      "diam_bottom_n",
      "bar_floor_n",
    ],
    side_alt: [
      "side_s",
      "bar_ceil_s",
      "diam_top_s",
      "diam_bottom_s",
      "bar_floor_s",
    ],
    no_side: ["no_side_tall_n"],
    no_side_alt: ["no_side_tall_s"],
  },
};

const baseModelStr = await readFile(INPUT_FILE, { encoding: "utf-8" });
const baseModel: SimpleModel = JSON.parse(baseModelStr);

for (const [shape, parts] of Object.entries(SHAPE_PART_ELEMENTS)) {
  const mainParts = new Set<string>();

  for (const [part, elements] of Object.entries(parts)) {
    const filename = `template_${shape}_window_frame_${part}.json`;

    if (MAIN_PARTS.includes(part as Part)) {
      for (const element of elements) {
        mainParts.add(element);
      }
    }

    const newModel: SimpleModel = {
      textures: baseModel.textures,
      elements: baseModel.elements.filter((element) =>
        elements.includes(element.name),
      ),
    };

    console.log(`Writing ${filename}`);
    await writeFile(resolve(OUTPUT_DIR, filename), JSON.stringify(newModel));
  }

  const mainFilename = `template_${shape}_window_frame.json`;
  const newModel: SimpleModel = {
    textures: baseModel.textures,
    elements: baseModel.elements.filter((element) =>
      mainParts.has(element.name),
    ),
    display: baseModel.display,
  };

  console.log(`Writing ${mainFilename}`);
  await writeFile(resolve(OUTPUT_DIR, mainFilename), JSON.stringify(newModel));
}
