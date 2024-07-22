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

package com.diontryban.flourish;

import com.diontryban.ash_api.options.ModOptionsManager;
import com.diontryban.flourish.options.FlourishOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flourish {
    public static final String MOD_ID = "flourish";
    public static final String MOD_NAME = "Flourish";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final ModOptionsManager<FlourishOptions> OPTIONS = new ModOptionsManager<>(MOD_ID, FlourishOptions.class);
}
