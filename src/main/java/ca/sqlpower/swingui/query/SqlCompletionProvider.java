/*
 * This file is part of DBArchitect.
 *
 * Copyright (c) 2026 villasoft (villasoft.cl@gmail.com)
 *
 * DBArchitect is a fork of Power*Architect, originally developed and
 * copyrighted by SQL Power Group Inc. (Copyright (c) 2008-2010).
 *
 * DBArchitect is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * DBArchitect is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.sqlpower.swingui.query;

import java.util.List;

/** Supplies SQL identifier completions to {@link SqlCompletionController}. */
public interface SqlCompletionProvider {

    /**
     * @param prefix the identifier fragment already typed (may be empty)
     * @param fullText the full editor contents
     * @param caretPosition caret offset inside {@code fullText}
     * @return candidate identifiers to offer, in display order (never null)
     */
    List<String> getCompletions(String prefix, String fullText, int caretPosition);
}
