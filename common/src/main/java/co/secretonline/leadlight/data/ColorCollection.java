package co.secretonline.leadlight.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.DyeColor;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public record ColorCollection<T>(T white, T orange, T magenta, T lightBlue, T yellow, T lime, T pink, T gray,
																 T lightGray, T cyan, T purple, T blue, T brown, T green, T red, T black) {
	public static final ColorCollection<DyeColor> VALUES = new ColorCollection<>(DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE, DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, DyeColor.LIGHT_GRAY, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
	public static final ColorCollection<String> NAMES = VALUES.map(DyeColor::getName);

	public static <T> ColorCollection<T> create(T value) {
		return new ColorCollection<>(value, value, value, value, value, value, value, value, value, value, value, value, value, value, value, value);
	}

	public static ColorCollection<String> prefixWithColor(ColorCollection<String> ids) {
		return zipMap(NAMES, ids, (color, id) -> color + "_" + id);
	}

	public List<T> asList() {
		ImmutableList.Builder<T> builder = ImmutableList.builderWithExpectedSize(16);
		Objects.requireNonNull(builder);
		this.forEach(builder::add);
		return builder.build();
	}

	public void forEach(Consumer<T> consumer) {
		consumer.accept(this.white);
		consumer.accept(this.orange);
		consumer.accept(this.magenta);
		consumer.accept(this.lightBlue);
		consumer.accept(this.yellow);
		consumer.accept(this.lime);
		consumer.accept(this.pink);
		consumer.accept(this.gray);
		consumer.accept(this.lightGray);
		consumer.accept(this.cyan);
		consumer.accept(this.purple);
		consumer.accept(this.blue);
		consumer.accept(this.brown);
		consumer.accept(this.green);
		consumer.accept(this.red);
		consumer.accept(this.black);
	}

	public <U> ColorCollection<U> map(Function<T, U> mapper) {
		return new ColorCollection<>(mapper.apply(this.white), mapper.apply(this.orange), mapper.apply(this.magenta), mapper.apply(this.lightBlue), mapper.apply(this.yellow), mapper.apply(this.lime), mapper.apply(this.pink), mapper.apply(this.gray), mapper.apply(this.lightGray), mapper.apply(this.cyan), mapper.apply(this.purple), mapper.apply(this.blue), mapper.apply(this.brown), mapper.apply(this.green), mapper.apply(this.red), mapper.apply(this.black));
	}

	public static <T, U, R> ColorCollection<R> zipMap(ColorCollection<T> first, ColorCollection<U> second, BiFunction<T, U, R> operation) {
		return new ColorCollection<>(operation.apply(first.white(), second.white()), operation.apply(first.orange(), second.orange()), operation.apply(first.magenta(), second.magenta()), operation.apply(first.lightBlue(), second.lightBlue()), operation.apply(first.yellow(), second.yellow()), operation.apply(first.lime(), second.lime()), operation.apply(first.pink(), second.pink()), operation.apply(first.gray(), second.gray()), operation.apply(first.lightGray(), second.lightGray()), operation.apply(first.cyan(), second.cyan()), operation.apply(first.purple(), second.purple()), operation.apply(first.blue(), second.blue()), operation.apply(first.brown(), second.brown()), operation.apply(first.green(), second.green()), operation.apply(first.red(), second.red()), operation.apply(first.black(), second.black()));
	}
}
