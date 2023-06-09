package fr.firstmegagame4.archeon.client.entities.renderers;

import fr.firstmegagame4.archeon.Archeon;
import fr.firstmegagame4.archeon.client.entities.models.SunstradiverEntityModel;
import fr.firstmegagame4.archeon.client.init.ArcheonEntityModelLayers;
import fr.firstmegagame4.archeon.entities.SunstradiverEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SunstradiverEntityRenderer extends MobEntityRenderer<SunstradiverEntity, SunstradiverEntityModel> {

	public SunstradiverEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SunstradiverEntityModel(context.getPart(ArcheonEntityModelLayers.SUNSTRADIVER)), 0.5f);
	}

	@Override
	public Identifier getTexture(SunstradiverEntity entity) {
		return Archeon.createId("textures/entities/sunstradiver.png");
	}

	public float getAnimationProgress(SunstradiverEntity parrotEntity, float f) {
		float flapProgress = MathHelper.lerp(f, (float) parrotEntity.prevFlapProgress().get(), (float) parrotEntity.flapProgress().get());
		float wingDeviation = MathHelper.lerp(f, (float) parrotEntity.prevMaxWingDeviation().get(), (float) parrotEntity.maxWingDeviation().get());
		return (MathHelper.sin(flapProgress) + 1.0F) * wingDeviation;
	}
}
