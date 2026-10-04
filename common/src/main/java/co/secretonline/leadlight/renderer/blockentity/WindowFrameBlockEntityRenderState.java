package co.secretonline.leadlight.renderer.blockentity;

import co.secretonline.leadlight.data.ConnectionInfo;
import co.secretonline.leadlight.data.FrameShape;
import co.secretonline.leadlight.component.WindowFrameContentsComponent;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import org.jspecify.annotations.NonNull;

public class WindowFrameBlockEntityRenderState extends BlockEntityRenderState {
	@NonNull
	public FrameShape frameShape;

	@NonNull
	public WindowFrameContentsComponent windowFrameContents;

	@NonNull
	public ConnectionInfo connections;

}
