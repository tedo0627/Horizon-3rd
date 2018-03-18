package tedo.LoginSystem.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.Entity;
import tedo.LoginSystem.LoginSystem;
import tedo.LoginSystem.Status;

public class LoginCommand extends Command{

	public LoginSystem main;

	public LoginCommand(LoginSystem main) {
		super("login", "ログインするコマンド");
		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("パスワード", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("int", new CommandParameter[]{
				new CommandParameter("パスワード", CommandParameter.ARG_TYPE_INT, false),
		});

		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールからこのコマンドを実行できません");
			return false;
		}
		Player player = (Player) sender;
		if (!this.main.login.containsKey(player)) {
			player.sendMessage("§a>>§b貴方は既にログイン済みです");
			return false;
		}
		if (Status.canLogin(player, args[0])){
			this.main.login.remove(player);
			player.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_NO_AI, false);
			sender.sendMessage("§a>>§bログインが完了しました");
			return true;
		}else{
			sender.sendMessage("§a>>§bパスワードが違います");
			return false;
		}
	}

}
