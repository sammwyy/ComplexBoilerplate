package dev._2lstudios.complexboilerplate.commands.impl;

import dev._2lstudios.complexboilerplate.commands.Command;
import dev._2lstudios.complexboilerplate.commands.CommandContext;
import dev._2lstudios.complexboilerplate.commands.CommandListener;

@Command(
  name = "world"
)
public class WorldSubCommand extends CommandListener {
    @Override
    public void onExecute(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("world");
    }
}
