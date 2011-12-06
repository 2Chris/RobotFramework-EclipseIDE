/**
 * Copyright 2011 Nitor Creations Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.otherone.robotframework.eclipse.editor.builder.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.otherone.robotframework.eclipse.editor.structure.ParsedString;

public class RFELine {
  public final int lineNo;
  public final int lineCharPos;
  public final List<ParsedString> arguments;
  public final Map<Object, Object> info = new LinkedHashMap<Object, Object>();

  public RFELine(int lineNo, int lineCharPos, List<ParsedString> arguments) {
    this.lineNo = lineNo;
    this.lineCharPos = lineCharPos;
    this.arguments = arguments;
  }

}