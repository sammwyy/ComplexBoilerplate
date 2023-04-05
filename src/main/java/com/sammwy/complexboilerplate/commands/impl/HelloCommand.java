package com.sammwy.complexboilerplate.commands.impl;

import com.sammwy.complexboilerplate.commands.Command;
import com.sammwy.complexboilerplate.commands.CommandContext;
import com.sammwy.complexboilerplate.commands.CommandListener;

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
