package com.sammwy.boilerplate.commands.impl;

import com.sammwy.boilerplate.commands.Command;
import com.sammwy.boilerplate.commands.CommandContext;
import com.sammwy.boilerplate.commands.CommandListener;

@Command(name = "hello")
public class HelloCommand extends CommandListener {
  public HelloCommand() {
    this.addSubcommand(new WorldSubCommand());
  }

  @Override
  public void onExecute(CommandContext ctx) {
    ctx.getExecutor().sendI18nMessage("hello");
  }
}
