package co.secretonline.leadlight.data;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public record FrameShapeCollection<T>(T large, T vertical, T horizontal, T square, T cross, T diamond) {
	public static final FrameShapeCollection<FrameShape> SHAPES = new FrameShapeCollection<>(FrameShape.LARGE, FrameShape.VERTICAL, FrameShape.HORIZONTAL, FrameShape.SQUARE, FrameShape.CROSS, FrameShape.DIAMOND);

	public Stream<T> stream() {
		return Stream.of(large, vertical, horizontal, square, cross, diamond);
	}

	public void forEach(Consumer<T> consumer) {
		consumer.accept(large);
		consumer.accept(vertical);
		consumer.accept(horizontal);
		consumer.accept(square);
		consumer.accept(cross);
		consumer.accept(diamond);
	}

	public <U> FrameShapeCollection<U> map(Function<T, U> mapper) {
		return new FrameShapeCollection<>(
			mapper.apply(large),
			mapper.apply(vertical),
			mapper.apply(horizontal),
			mapper.apply(square),
			mapper.apply(cross),
			mapper.apply(diamond));
	}

	public static <T, U, R> FrameShapeCollection<R> zipMap(FrameShapeCollection<T> first, FrameShapeCollection<U> second, BiFunction<T, U, R> operation) {
		return new FrameShapeCollection<>(
			operation.apply(first.large(), second.large()),
			operation.apply(first.vertical(), second.vertical()),
			operation.apply(first.horizontal(), second.horizontal()),
			operation.apply(first.square(), second.square()),
			operation.apply(first.cross(), second.cross()),
			operation.apply(first.diamond(), second.diamond()));
	}
}
