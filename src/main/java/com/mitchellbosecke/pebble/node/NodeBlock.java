/*******************************************************************************
 * This file is part of Pebble.
 * 
 * Original work Copyright (c) 2009-2013 by the Twig Team
 * Modified work Copyright (c) 2013 by Mitchell Bösecke
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.node;

import com.mitchellbosecke.pebble.compiler.Compiler;

public class NodeBlock extends AbstractNode {

	public static final String BLOCK_PREFIX = "block_";

	private NodeBody body;
	private String name;

	public NodeBlock(int lineNumber, String name) {
		this(lineNumber, name, null);
	}

	public NodeBlock(int lineNumber, String name, NodeBody body) {
		super(lineNumber);
		this.body = body;
		this.name = name;
	}

	public void setBody(NodeBody body) {
		this.body = body;
	}

	@Override
	public void compile(Compiler compiler) {
		compiler.write(
				String.format("public String %s%s() throws com.mitchellbosecke.pebble.error.PebbleException {\n",
						BLOCK_PREFIX, this.name)).indent();

		compiler.write("StringBuilder builder = new StringBuilder();\n");

		compiler.subcompile(body);

		compiler.raw("\n").write("return builder.toString();");

		compiler.raw("\n").outdent().write("}\n");

	}

}
