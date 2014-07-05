package net.gasull.well.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import net.gasull.well.WellCore;
import net.gasull.well.WellPermissionManager.WellPermissionException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Common command handler for well-designed plugins.
 */
public class WellCommandHandler implements CommandExecutor {

	/** The name of the command this handler is handling. */
	private String commandName;

	/** The registered subcommands. */
	Map<String, WellCommand<?>> subCommands = new HashMap<>();

	/** The usage error message. */
	private final String usageError;

	/**
	 * Instantiates a new well command handler.
	 * 
	 * @param command
	 *            the command name
	 */
	private WellCommandHandler(String command) {
		this.commandName = command;
		this.usageError = ChatColor.DARK_RED + WellCore.conf().getString("lang.command.error.usage", "Usage: /%command% %subcommand% %args%");
	}

	/**
	 * Bind.
	 * 
	 * @param plugin
	 *            the plugin
	 * @param command
	 *            the command
	 * @return the well command handler
	 */
	public static WellCommandHandler bind(JavaPlugin plugin, String command) {
		WellCommandHandler handler = new WellCommandHandler(command);
		plugin.getCommand(command).setExecutor(handler);
		return handler;
	}

	/**
	 * Register.
	 * 
	 * @param subCommand
	 *            the sub command
	 * @return this, for chaining
	 */
	public WellCommandHandler attach(WellCommand<?> subCommand) {
		subCommands.put(subCommand.getName(), subCommand);
		return this;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			return false;
		}

		WellCommand<?> wellCommand = subCommands.get(args[0]);

		if (wellCommand != null) {
			String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

			if (wellCommand.checkArguments(sender, subArgs)) {
				try {
					wellCommand.execute(sender, subArgs);
				} catch (WellPermissionException e) {
					WellCore.logger().log(Level.INFO, "Permission revoked to {}: {}", new String[] { sender.getName(), e.getKey() });
				}
			} else {
				String argsString = StringUtils.join(wellCommand.getRequiredArgs(), " ");
				String optionalsString = wellCommand.getOptionalArgs() == null ? "" : String.format(" [%s]"
						+ StringUtils.join(wellCommand.getOptionalArgs(), " "));
				sender.sendMessage(usageError.replace("%command%", commandName).replace("%subcommand%", wellCommand.getName())
						.replace("%args%", argsString + optionalsString));
			}

			return true;
		}

		return false;
	}
}
