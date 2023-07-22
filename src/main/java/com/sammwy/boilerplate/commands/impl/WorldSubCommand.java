package com.sammwy.boilerplate.commands.impl;

import com.sammwy.boilerplate.commands.Command;
import com.sammwy.boilerplate.commands.CommandContext;
import com.sammwy.boilerplate.commands.CommandListener;

@Command(name = "world")
public class WorldSubCommand extends CommandListener {
  @Override
  public void onExecute(CommandContext ctx) {
    ctx.getExecutor().sendI18nMessage("world");
  }
}
