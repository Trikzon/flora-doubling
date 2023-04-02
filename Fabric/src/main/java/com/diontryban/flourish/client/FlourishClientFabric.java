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

package com.diontryban.flourish.client;

import com.diontryban.flourish.Flourish;
import net.fabricmc.api.ClientModInitializer;

public class FlourishClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Flourish.init();
        FlourishClient.init();
    }
}
