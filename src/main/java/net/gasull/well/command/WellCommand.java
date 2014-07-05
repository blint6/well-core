package net.gasull.well.command;

import net.gasull.well.WellCore;
import net.gasull.well.WellPermissionManager.WellPermissionException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Modelizes a command, or a subcommand (commands can have subcommands that can
 * have subcommands).
 * 
 * @param <P>
 *            the generic type of command sender
 */
public abstract class WellCommand<P extends CommandSender> {

	/** The must be player error. */
	private final String mustBePlayerError;

	/** The permission error message. */
	private final String permissionMessage;

	/**
	 * Instantiates a new command.
	 */
	public WellCommand() {
		this.mustBePlayerError = ChatColor.DARK_RED + WellCore.conf().getString("lang.command.error.mustBePlayer", "You must be a player to run this command");
		this.permissionMessage = ChatColor.DARK_RED + WellCore.conf().getString("lang.command.error.permission", "to run this command");
	}

	/**
	 * Executes the command, with checks.
	 * 
	 * @param sender
	 *            the sender
	 * @param args
	 *            the args
	 * @throws WellPermissionException
	 *             the well permission exception
	 */
	public void execute(CommandSender sender, String[] args) throws WellPermissionException {
		P castedSender = castSender(sender);

		if (castedSender != null && checkArguments(sender, args)) {

			if (getPermission() != null && sender instanceof Player) {
				WellCore.permission().can((Player) sender, permissionMessage, getPermission());
			}

			try {
				String successMsg = handleCommand(castedSender, args);

				if (successMsg != null) {
					sender.sendMessage(ChatColor.GREEN + successMsg);
				}
			} catch (WellCommandException e) {
				sender.sendMessage(ChatColor.RED + e.getErrorMessage());
			}
		}
	}

	/**
	 * Checks if is of the requested type, and return casted if possible.
	 * 
	 * @param sender
	 *            the sender
	 * @return the casted sender
	 */
	@SuppressWarnings("unchecked")
	private P castSender(CommandSender sender) {
		try {
			return (P) sender;
		} catch (ClassCastException e) {
			sender.sendMessage(mustBePlayerError);
			return null;
		}
	}

	/**
	 * Check arguments.
	 * 
	 * @param sender
	 *            the sender
	 * @param args
	 *            the arguments to check
	 * @return true, if successful
	 */
	public boolean checkArguments(CommandSender sender, String[] args) {

		// Compare full command (with plugin command name) with sub arguments
		if (getRequiredArgs() == null || args.length >= getRequiredArgs().length) {
			return true;
		}

		return false;
	}

	/**
	 * Handle the command. Arguments already have been checked according to
	 * their definition in constructor.
	 * 
	 * @param sender
	 *            the sender
	 * @param args
	 *            the command's arguments (without the command's name)
	 * @return the success message
	 * @throws WellCommandException
	 *             the well command exception
	 * @throws WellPermissionException
	 *             the well permission exception
	 */
	public abstract String handleCommand(P sender, String[] args) throws WellCommandException, WellPermissionException;

	/**
	 * Gets the name of the command.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the required arguments to run the command properly.
	 * 
	 * @return the required args
	 */
	public abstract String[] getRequiredArgs();

	/**
	 * Gets the optional arguments.
	 * 
	 * @return the optional args
	 */
	public abstract String[] getOptionalArgs();

	/**
	 * Gets the permission required to run the command.
	 * 
	 * @return the permission
	 */
	public abstract String getPermission();
}
