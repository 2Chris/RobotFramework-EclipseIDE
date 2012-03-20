/**
 * Copyright 2012 Nitor Creations Oy
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
package com.nitorcreations.robotframework.eclipseide.internal.hyperlinks;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.nitorcreations.robotframework.eclipseide.builder.parser.ArgumentPreParser;
import com.nitorcreations.robotframework.eclipseide.builder.parser.RFELexer;
import com.nitorcreations.robotframework.eclipseide.builder.parser.RFELine;
import com.nitorcreations.robotframework.eclipseide.builder.parser.RFELine.LineType;
import com.nitorcreations.robotframework.eclipseide.builder.parser.RFEPreParser;
import com.nitorcreations.robotframework.eclipseide.structure.ParsedString;
import com.nitorcreations.robotframework.eclipseide.structure.ParsedString.ArgumentType;

/**
 * This hyperlink detector creates hyperlinks for keyword calls, e.g.
 * "  SomeKeyword FooArgument" --> "SomeKeyword" is linked.
 * 
 * @author xkr47
 */
public class KeywordCallHyperlinkDetector extends HyperlinkDetector {

    @Override
    public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
        if (region == null || textViewer == null) {
            return null;
        }

        IDocument document = textViewer.getDocument();
        if (document == null) {
            return null;
        }

        int offset = region.getOffset();

        int lineNumber;
        try {
            lineNumber = document.getLineOfOffset(offset);
        } catch (BadLocationException ex) {
            return null;
        }

        List<RFELine> lines;
        try {
            RFELexer lexer = new RFELexer(document);
            lines = lexer.lex();
            new RFEPreParser(null, lines).preParse();
            ArgumentPreParser app = new ArgumentPreParser();
            app.setRange(lines);
            app.parseAll();
        } catch (CoreException e) {
            e.printStackTrace();
            return null;
        }

        RFELine rfeLine = lines.get(lineNumber);
        ParsedString argument = rfeLine.getArgumentAt(offset);
        if (argument == null || argument.getType() != ArgumentType.KEYWORD_CALL) {
            return null;
        }

        String linkString = argument.getUnescapedValue();
        IRegion linkRegion = new Region(argument.getArgCharPos(), argument.getValue().length());
        return getLinks(document, linkString, linkRegion, LineType.KEYWORD_TABLE_KEYWORD_BEGIN);
    }
}