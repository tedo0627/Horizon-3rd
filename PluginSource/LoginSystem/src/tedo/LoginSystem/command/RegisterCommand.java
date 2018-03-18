package tedo.LoginSystem.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.Entity;
import tedo.LoginSystem.LoginSystem;
import tedo.LoginSystem.Status;

public class RegisterCommand extends Command{

	public LoginSystem main;

	public RegisterCommand(LoginSystem main) {
		super("register", "アカウント作成コマンド");
		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("パスワード1", CommandParameter.ARG_TYPE_STRING, false),
				new CommandParameter("パスワード2", CommandParameter.ARG_TYPE_STRING, false),
		});
		this.addCommandParameters("int", new CommandParameter[]{
				new CommandParameter("パスワード1", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("パスワード2", CommandParameter.ARG_TYPE_INT, false),
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
		if (!this.main.register.containsKey(player)) {
			player.sendMessage("§a>>§b貴方は既にアカウントを登録済みです");
			return false;
		}
		if (!args[0].equals(args[1])) {
			player.sendMessage("§a>>§b入力した二つのパスワードを一緒にしてください");
			return false;
		}
		if (player.getName().toLowerCase().indexOf(args[0].toLowerCase()) != -1) {
			player.sendMessage("§a>>§b安易なパスワードは使えません");
			return false;
		}
		Status.register(player, args[0]);
		player.setDataFlag(Entity.DATA_FLAGS, Entity.DATA_FLAG_NO_AI, false);
		this.main.register.remove(player);
		player.sendMessage("§a>>§b貴方のパスワードは" + args[0] + "で登録されました");
		player.sendMessage("§a>>§bパスワードは忘れないようにしてください");
		return true;
	}
}
