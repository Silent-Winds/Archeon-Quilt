package fr.firstmegagame4.archeon;

import com.mmodding.mmodding_lib.library.base.MModdingModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import fr.firstmegagame4.archeon.init.ArcheonBlocks;
import fr.firstmegagame4.archeon.init.ArcheonItems;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;

import java.util.ArrayList;
import java.util.List;

public class Archeon implements MModdingModInitializer {

	@Override
	public @Nullable Config getConfig() {
		return null;
	}

	@Override
	public List<ElementsInitializer> getElementsInitializers() {
		List<ElementsInitializer> elementsInitializers = new ArrayList<>();
		elementsInitializers.add(new ArcheonBlocks());
		elementsInitializers.add(new ArcheonItems());
		return elementsInitializers;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		MModdingModInitializer.super.onInitialize(mod);
	}
}