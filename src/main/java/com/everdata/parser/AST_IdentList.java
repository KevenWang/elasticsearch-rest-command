/* Generated By:JJTree: Do not edit this line. AST_IdentList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.everdata.parser;

import java.util.ArrayList;

public
class AST_IdentList extends SimpleNode {
  public AST_IdentList(int id) {
    super(id);
  }

  public AST_IdentList(CommandParser p, int id) {
    super(p, id);
  }
  /** Names of the columns/tables. */
  protected ArrayList<String> names = new ArrayList<String>();

  /**
   * Gets the names of the columns/tables.
   */
  public String[] getNames() {
    return names.toArray(new String[names.size()]);
  }
}
/* JavaCC - OriginalChecksum=20bbc1ecd7baf6197e342c3363d75e6e (do not edit this line) */
