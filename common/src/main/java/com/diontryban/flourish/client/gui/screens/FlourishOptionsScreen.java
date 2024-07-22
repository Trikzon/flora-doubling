/*
 * This file is part of Flourish.
 * A copy of this program can be found at https://github.com/Trikzon/flourish.
 * Copyright (C) 2023 Dion Tryban
 *
 * Flourish is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Flourish is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Flourish. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.flourish.client.gui.screens;

import com.diontryban.ash_api.client.gui.screens.ModOptionsScreen;
import com.diontryban.ash_api.options.ModOptionsManager;
import com.diontryban.flourish.Flourish;
import com.diontryban.flourish.options.FlourishOptions;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FlourishOptionsScreen extends ModOptionsScreen<FlourishOptions> {
    public FlourishOptionsScreen(ModOptionsManager<FlourishOptions> config, Screen parent) {
        super(Component.literal(Flourish.MOD_NAME), config, parent);
    }

    @Override
    protected void addOptions() {
        if (list == null) { return; }

        this.list.addBig(OptionInstance.createBoolean(
                "flourish.options.wither_rose",
                value -> Tooltip.create(Component.translatable("flourish.options.wither_rose.tooltip")),
                options.get().witherRose,
                value -> options.get().witherRose = value
        ));
        this.list.addBig(OptionInstance.createBoolean(
                "flourish.options.torchflower",
                value -> Tooltip.create(Component.translatable("flourish.options.torchflower.tooltip")),
                options.get().torchflower,
                value -> options.get().torchflower = value
        ));
        this.list.addBig(OptionInstance.createBoolean(
                "flourish.options.use_tall_flower_behavior",
                value -> Tooltip.create(Component.translatable("flourish.options.use_tall_flower_behavior.tooltip")),
                options.get().useTallFlowerBehavior,
                value -> options.get().useTallFlowerBehavior = value
        ));
    }
}
