package tedo.EconomySystemAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class MoneyCommand extends Command{

	public EconomySystemAPI main;

	public MoneyCommand(EconomySystemAPI main) {
		super("money", "経済系のコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{});
		this.addCommandParameters("see", new CommandParameter[]{
				new CommandParameter("see", new String[]{"see"}),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
		});
		this.addCommandParameters("pay", new CommandParameter[]{
				new CommandParameter("pay", new String[]{"pay"}),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
				new CommandParameter("金額", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("top", new CommandParameter[]{
				new CommandParameter("top", new String[]{"top"}),
				new CommandParameter("ページ数", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("add", new CommandParameter[]{
				new CommandParameter("add", new String[]{"add"}),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
				new CommandParameter("金額", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("del", new CommandParameter[]{
				new CommandParameter("del", new String[]{"del"}),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
				new CommandParameter("金額", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("set", new CommandParameter[]{
				new CommandParameter("set", new String[]{"set"}),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
				new CommandParameter("金額", CommandParameter.ARG_TYPE_INT, false),
		});

		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length < 1) {
			if (sender instanceof Player) {
				String name = sender.getName();
				long money = this.main.getMoney(name);
				sender.sendMessage("§a>>§b貴方は" + money + "円所持してます");
				return true;
			} else {
				sender.sendMessage("§a>>§bコンソールからこのコマンドを実行することはできません");
				return false;
			}
		}
		String name;
		long money;
		switch (args[0]) {
			case "see":
				name = this.main.getName(args[1]);
				if (this.main.isMoney(name)) {
					money = this.main.getMoney(name);
					sender.sendMessage("§a>>§b" + name + "は" + money + "円所持してます");
					return true;
				}else{
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}

			case "top":
				int page = 0;
				int h = this.main.money.size() / 5 + 1;
				int i = 1;
				if (args.length > 1) {
					i = Integer.parseInt(args[1]);
				}
				if (i <= h) {
					page = i;
				}else{
					page = h;
				}
				sender.sendMessage("§e====所持金ランキング" + page + "/" + h + "====");
				List<Entry<String, Long>> top = new ArrayList<Entry<String, Long>>(this.main.money.entrySet());
				Collections.sort(top, new Comparator<Entry<String, Long>>() {
					public int compare(Entry<String, Long> obj1, Entry<String, Long> obj2) {
						return obj2.getValue().compareTo(obj1.getValue());
					}
				});
				int c = 1;
				for (Entry<String, Long> entry : top) {
					if (c >= page * 5 - 4 && c <= page * 5) {
						sender.sendMessage("§b" + c + "位 | " + entry.getKey() + " : " + entry.getValue() + "円");
					}
					c++;
					if (c > page * 5) {
						return true;
					}
				}
				return true;

			case "pay":
				if (!(sender instanceof Player)) {
					sender.sendMessage("§a>>§bコンソールからこのコマンドを実行することはできません");
					return false;
				}
				name = this.main.getName(args[1]);
				if (!this.main.isMoney(name)) {
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}
				money = this.main.getMoney(sender.getName());
				long data = Long.parseLong(args[2]);
				if (data < 1) {
					sender.sendMessage("§a>>§b0円より多くしてください");
					return false;
				}
				if (money >= data) {
					this.main.delMoney(sender.getName().toLowerCase(), data);
					this.main.addMoney(name, data);
					sender.sendMessage("§a>>§b" + name + "に" + data + "円渡しました");
					return true;
				}else{
					sender.sendMessage("§a>>§b貴方はそんなにお金を持っていません");
					return false;
				}

			case "add":
				if (!sender.hasPermission("op")) {
					sender.sendMessage("§a>>§b貴方はこのコマンドを使用する権限がありません");
					return false;
				}
				name = this.main.getName(args[1]);
				if (this.main.isMoney(name)) {
					money = Long.parseLong(args[2]);
					this.main.addMoney(name, money);
					sender.sendMessage("§a>>§b" + name + "に" + money + "円渡しました");
					return true;
				}else{
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}

			case "del":
				if (!sender.hasPermission("op")) {
					sender.sendMessage("§a>>§b貴方はこのコマンドを使用する権限がありません");
					return false;
				}
				name = this.main.getName(args[1]);
				if (!this.main.isMoney(name)) {
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}
				money = Long.parseLong(args[2]);
				if (money <= this.main.getMoney(name)) {
					this.main.delMoney(name, money);
					sender.sendMessage("§a>>§b" + name + "から" + money + "円没収しました");
					return true;
				}else{
					sender.sendMessage("§a>>§b" + name + "はそんなにお金を所持していません");
					return false;
				}

			case "set":
				if (!sender.hasPermission("op")) {
					sender.sendMessage("§a>>§b貴方はこのコマンドを使用する権限がありません");
					return false;
				}
				name = this.main.getName(args[1]);
				if (!this.main.isMoney(name)) {
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}
				if (this.main.isMoney(name)) {
					money = Long.parseLong(args[2]);
					this.main.setMoney(name, money);
					sender.sendMessage("§a>>§b" + name + "の所持金を" + money + "円にしました");
					return true;
				}else{
					sender.sendMessage("§a>>§b" + args[1] + "は存在しません");
					return false;
				}
			}
		return false;
	}

}
