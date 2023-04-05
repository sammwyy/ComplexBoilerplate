package com.sammwy.complexboilerplate.commands.impl;

import com.sammwy.complexboilerplate.commands.Command;
import com.sammwy.complexboilerplate.commands.CommandContext;
import com.sammwy.complexboilerplate.commands.CommandListener;

@Command(name = "world")
public class WorldSubCommand extends CommandListener {
  @Override
  public void onExecute(CommandContext ctx) {
    ctx.getExecutor().sendI18nMessage("world");
  }
}
