package tedo.ItemStorage;

import java.text.NumberFormat;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;

public class ItemCommand extends Command{

	public ItemStorage main;

	public ItemCommand(ItemStorage main) {
		super("item", "アイテムストレージ関連のコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("upload", false, new String[]{"upload", "up"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("個数", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("upload2", new CommandParameter[]{
				new CommandParameter("upload", true, new String[]{"upload", "up"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, true),
				new CommandParameter("アイテムMETA", CommandParameter.ARG_TYPE_INT, true),
				new CommandParameter("個数", CommandParameter.ARG_TYPE_INT, true),
		});

		this.addCommandParameters("download1", new CommandParameter[]{
				new CommandParameter("download", false, new String[]{"download", "dl"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("個数", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("download2", new CommandParameter[]{
				new CommandParameter("download", true, new String[]{"download", "dl"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, true),
				new CommandParameter("アイテムMETA", CommandParameter.ARG_TYPE_INT, true),
				new CommandParameter("個数", CommandParameter.ARG_TYPE_INT, true),
		});

		this.addCommandParameters("count1", new CommandParameter[]{
				new CommandParameter("count", false, new String[]{"count"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("count2", new CommandParameter[]{
				new CommandParameter("count", true, new String[]{"count"}),
				new CommandParameter("アイテムID", CommandParameter.ARG_TYPE_INT, true),
				new CommandParameter("アイテムMETA", CommandParameter.ARG_TYPE_INT, true),
		});

		this.addCommandParameters("list", new CommandParameter[]{
				new CommandParameter("list", new String[]{"list"}),
		});

		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールから実行することはできません");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage("§a>>§b/item upload [ID] [メタデータ] [個数]");
			return false;
		}
		Item item;
		Player player = (Player) sender;
		switch (args[0]) {
			case "upload":
			case "up":
				if (args.length < 4) {
					item = Item.fromString(args[1]);
					item = Item.get(item.getId(), item.getDamage(), Integer.valueOf(args[2]));
				} else {
					item = Item.fromString(args[1]);
					item = Item.get(item.getId(), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				}
				if (item.getCount() < 1) {
					player.sendMessage("§a>>§bアイテムの数は1以上にしてください");
					return false;
				}
				if (player.getInventory().contains(item)) {
					player.getInventory().removeItem(item);
					this.main.addItem(player, item);
					player.sendMessage("§a>>§bアイテムをストレージに保存しました");
					return true;
				} else {
					player.sendMessage("§a>>§b指定されたアイテムはそんなにありません");
					return false;
				}

			case "download":
			case "dl":
				if (args.length < 4) {
					item = Item.fromString(args[1]);
					item = Item.get(item.getId(), item.getDamage(), Integer.valueOf(args[2]));
				} else {
					item = Item.fromString(args[1]);
					item = Item.get(item.getId(), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				}
				if (item.getCount() < 1) {
					player.sendMessage("§a>>§bアイテムの数は1以上にしてください");
					return false;
				}
				if (!this.main.hasItem(player, item)) {
					player.sendMessage("§a>>§bストレージに指定されたアイテムはありません");
					return false;
				}
				if (player.getInventory().canAddItem(item)) {
					player.getInventory().addItem(item);
					this.main.delItem(player, item);
					player.sendMessage("§a>>§bアイテムをストレージから引き出しました");
					return true;
				} else {
					player.sendMessage("§a>>§bインベントリーがいっぱいです");
					return false;
				}

			case "count":
				if (args.length < 4) {
					item = Item.fromString(args[1]);
				} else {
					item = Item.fromString(args[1] + ":" + args[2]);
				}
				if (this.main.hasItem(player, item)) {
					int i = this.main.getItemCount(player, item);
					player.sendMessage("§a>>§b" + item.getName() + "は" + NumberFormat.getNumberInstance().format(i) + "個あります");
					return true;
				}else{
					player.sendMessage("§a>>§b" + item.getName() + "はストレージにありません");
					return false;
				}

			case "list":
				CompoundTag nbt = player.namedTag.getCompound("ItemStorage").clone();
				Map<String, Tag> tag =nbt.getTags();
				if (tag.size() < 1) {
					player.sendMessage("§a>>§bストレージには何もありません");
					return true;
				}
				player.sendMessage("§a>>§bアイテムリスト");
				for (String id : tag.keySet()) {
					int count = nbt.getInt(id);
					String name = Item.fromString(id).getName();
					for (int i = 15 - name.length(); i > 0; i--) {
						name = name + " ";
					}
					for (int i = 6 - id.length(); i > 0; i--) {
						id = id + " ";
					}
					player.sendMessage("§a>>§b" + name + " | " + id + " | " + NumberFormat.getNumberInstance().format(count) + "個");
				}
				return true;
		}
		return false;
	}
}
