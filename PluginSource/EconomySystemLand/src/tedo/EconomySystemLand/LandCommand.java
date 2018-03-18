package tedo.EconomySystemLand;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;

public class LandCommand extends Command{

	public EconomySystemLand main;

	public LandCommand(EconomySystemLand main) {
		super("land", "土地保護関連のコマンド");

		this.getCommandParameters().clear();
		this.addCommandParameters("default", new CommandParameter[]{
				new CommandParameter("pos1", new String[]{"pos1"}),
		});
		this.addCommandParameters("pos2", new CommandParameter[]{
				new CommandParameter("pos2", new String[]{"pos2"}),
		});
		this.addCommandParameters("buy", new CommandParameter[]{
				new CommandParameter("buy", new String[]{"buy"}),
		});
		this.addCommandParameters("sell", new CommandParameter[]{
				new CommandParameter("sell", new String[]{"sell"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("give", new CommandParameter[]{
				new CommandParameter("give", new String[]{"give"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
		});
		this.addCommandParameters("move", new CommandParameter[]{
				new CommandParameter("move", new String[]{"move"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
		});
		this.addCommandParameters("setmove", new CommandParameter[]{
				new CommandParameter("setmove", new String[]{"setmove"}),
		});
		this.addCommandParameters("status", new CommandParameter[]{
				new CommandParameter("status", new String[]{"status"}),
		});
		this.addCommandParameters("here", new CommandParameter[]{
				new CommandParameter("here", new String[]{"here"}),
		});
		this.addCommandParameters("show", new CommandParameter[]{
				new CommandParameter("show", new String[]{"show"}),
		});
		this.addCommandParameters("invi1", new CommandParameter[]{
				new CommandParameter("invi", new String[]{"invi"}),
				new CommandParameter("add", new String[]{"add"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
		});
		this.addCommandParameters("invi2", new CommandParameter[]{
				new CommandParameter("invi", new String[]{"invi"}),
				new CommandParameter("del", new String[]{"del"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
				new CommandParameter("プレイヤー名", CommandParameter.ARG_TYPE_PLAYER, false),
		});
		this.addCommandParameters("invi3", new CommandParameter[]{
				new CommandParameter("invi", new String[]{"invi"}),
				new CommandParameter("list", new String[]{"list"}),
				new CommandParameter("土地番号", CommandParameter.ARG_TYPE_INT, false),
		});

		this.main = main;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールから実行することはできません");
			return false;
		}
		Player player = (Player) sender;
		String name = player.getName().toLowerCase();
		long money;
		int id;
		Land land;
		switch (args[0]) {
			case "pos1":
				this.main.pos.put(name, true);
				player.sendMessage("§a>>§b一つ目の位置のブロックをタッチして下さい");
				return true;

			case "pos2":
				this.main.pos.put(name, false);
				player.sendMessage("§a>>§b二つ目の位置のブロックをタッチして下さい");
				return true;

			case "buy":
				if (!this.main.pos1.containsKey(name)) {
					player.sendMessage("§a>>§b一つ目の位置を決めてください");
					return false;
				}
				if (!this.main.pos2.containsKey(name)) {
					player.sendMessage("§a>>§b二つ目の位置を決めてください");
					return false;
				}
				if (!this.main.canProtectLevel(player)) {
					player.sendMessage("§a>>§bこのワールドは保護できません");
					return false;
				}
				if (!this.main.canBuy(player)) {
					player.sendMessage("§a>>§b範囲内に保護されている場所があります");
					return false;
				}
				if ((boolean) ((HashMap<String, Object>) this.main.co.get("ログイン時間によって保護できるブロックの量を決めるか")).get("有効させるか")) {
					if (this.main.blockcount.get(name) - this.main.land.getMyLandBlockCount(name) < this.main.getBlockCount((Player) sender)) {
						sender.sendMessage("§a>>§b保護できるブロック数が足りません");
						sender.sendMessage("§a>>§b鯖にログインしていると保護できるブロックの数が増えます");
						break;
					}
				}
				if ((boolean) ((HashMap<String, Object>) this.main.co.get("保護できる土地の最大数を決めるか")).get("有効させるか")) {
					HashMap<String, Object> d = (HashMap<String, Object>) this.main.co.get("保護できる土地の最大数を決めるか");
					HashMap<String, String> level = new HashMap<String, String>();
					String[] l = ((String) d.get("有効するワールド")).split(":");
					for (int i = 0; i < l.length; i++) {
						level.put(l[i], l[i]);
					}
					if (level.containsKey(((Player) sender).getLevel().getName())) {
						int co = this.main.land.getMyLandCount(name, ((Player) sender).getLevel().getName());
						if (co >= ((int) d.get("最大数"))) {
							sender.sendMessage("§a>>§b貴方はこのワールドにこれ以上保護している場所を増やすことはできません");
							sender.sendMessage("§a>>§b保護する場合は必要のない土地を売却してください");
							break;
						}
					}
				}
				if (this.main.isSide()) {
					if (!this.main.hasSide((Player) sender)) {
						HashMap<String, Object> data = (HashMap<String, Object>) this.main.co.get("保護できる土地の1辺の大きさを決めるか");
						int i1 = (int) data.get("保護できる最小の1辺のブロックの数");
						int i2 = (int) data.get("保護できる最大の1辺のブロックの数");
						sender.sendMessage("§a>>§b土地の1辺の長さは" + String.valueOf(i1) + "から" + String.valueOf(i2) + "にしてください");
						break;
					}
				}
				money = this.main.getCount((Player) sender);
				if (this.main.economy.getMoney(name) >= money) {
					this.main.economy.delMoney(name, money);
					this.main.showBlock((Player) sender);
					this.main.addLand((Player) sender);
					sender.sendMessage("§a>>§b土地を購入しました");
					return true;
				}else{
					sender.sendMessage("§a>>§bお金が足りません");
					return false;
				}

			case "sell":
				id = Integer.parseInt(args[1]);
				if (!this.main.land.isLand(id)) {
					sender.sendMessage("§a>>§bその土地は存在しません");
					return false;
				}
				land = this.main.land.getLand(id);
				if (land.isOwner(name) || sender.isOp()) {
					int count = land.getBlockCount();
					int amount = count / 2;
					this.main.economy.addMoney(name, amount);
					this.main.delLand(id);
					sender.sendMessage("§a>>§b土地を売却しました");
					return true;
				}else{
					sender.sendMessage("§a>>§b貴方はその土地の所有者ではありません");
					return false;
				}

			case "give":
				id = Integer.parseInt(args[1]);
				if (!this.main.land.isLand(id)) {
					sender.sendMessage("§a>>§bその土地は存在しません");
					return false;
				}
				land = this.main.land.getLand(id);
				if (!land.isOwner(name)) {
					sender.sendMessage("§a>>§b貴方はその土地の所有者ではありません");
					return false;
				}
				if (this.main.economy.isMoney(args[2].toLowerCase())) {
					land.setOwner(args[2].toLowerCase());
					land.clearInvite();
					this.main.land.updateLand(land);
					sender.sendMessage("§a>>§b" + args[2] + "に土地を渡しました");
					return true;
				}else{
					sender.sendMessage("§a>>§bそのプレイヤーは存在しません");
					return false;
				}

			case "here":
				int x = (int) ((Player) sender).x;
				int z = (int) ((Player) sender).z;
				String level = ((Player) sender).getLevel().getName();
				if (this.main.land.isLand(x, z, level)) {
					land = this.main.land.getLand(x, z, level);
					String owner = land.owner;
					sender.sendMessage("§a>>§bこの土地は" + owner + "が所有しており、土地番号は" + String.valueOf(land.id) + "番です");
				}else{
					sender.sendMessage("§a>>§bこの土地は誰も所有していません");
				}
				return true;

			case "status":
				if ((boolean) ((HashMap<String, Object>) this.main.co.get("ログイン時間によって保護できるブロックの量を決めるか")).get("有効させるか")) {
					sender.sendMessage("§a>>§b貴方が保護できる残りのブロック数は" + String.valueOf(this.main.blockcount.get(name) - this.main.land.getMyLandBlockCount(name)) + "ブロック");
				}
				if ((boolean) ((HashMap<String, Object>) this.main.co.get("保護できる土地の最大数を決めるか")).get("有効させるか")) {
					HashMap<String, Object> d = (HashMap<String, Object>) this.main.co.get("保護できる土地の最大数を決めるか");
					HashMap<String, String> levels = new HashMap<String, String>();
					String[] l = ((String) d.get("有効するワールド")).split(":");
					for (int i = 0; i < l.length; i++) {
						levels.put(l[i], l[i]);
					}
					if (levels.containsKey(((Player) sender).getLevel().getName())) {
						int co = this.main.land.getMyLandCount(name, ((Player) sender).getLevel().getName());
						sender.sendMessage("§a>>§b貴方はこのワールドで残り" + ((int) d.get("最大数") - co) + "個の土地を保護できます");
					}
				}
				sender.sendMessage("§a>>§b貴方が所有している土地");
				this.main.land.getMyLands(name).forEach((lands) -> {
					String x1 = String.valueOf(lands.x1);
					String z1 = String.valueOf(lands.z1);
					String x2 = String.valueOf(lands.x2);
					String z2 = String.valueOf(lands.z2);
					String id1 = String.valueOf(lands.id);
					String le = String.valueOf(lands.level);
					sender.sendMessage("§a>>§b土地番号" + id1 + " : x " + x1 + " ^ " + x2 + " : z " + z1 + " ^ " + z2 + " : world " + le);
				});
				return true;

			case "move":
				id = Integer.parseInt(args[1]);
				if (!this.main.land.isLand(id)) {
					sender.sendMessage("§a>>§bその土地は存在しません");
					return false;
				}
				land = this.main.land.getLand(id);
				if (land.isOwner(name) || land.isInvite(name) || sender.isOp()) {
					((Player) sender).teleport(land.getPosition());
					((Player) sender).sendTip("§bテレポートしました");
					return true;
				}else{
					sender.sendMessage("§a>>§bその土地にテレポートできません");
					return false;
				}

			case "setmove":
				int x1 = (int) player.x;
				int y1 = (int) player.y;
				int z1 = (int) player.z;
				String level1 = player.getLevel().getName();
				if (!this.main.land.isLand(x1, z1, level1)) {
					sender.sendMessage("§a>>§bここは誰の土地でもありません");
					return false;
				}
				land = this.main.land.getLand(x1, z1, level1);
				if (land.isOwner(name)) {
					land.setPosition(new Position(x1, y1, z1, player.getLevel()));
					this.main.land.updateLand(land);
					sender.sendMessage("§a>>§bテレポートの場所を決めました");
					return true;
				}else{
					sender.sendMessage("§a>>§b貴方はこの土地のテレポートをセットできません");
					return false;
				}

			case "show":
				ArrayList<Land> lands1 = this.main.land.getMyLands(name, ((Player)sender).getLevel().getName());
				lands1.forEach((lands) -> {
					this.main.showBlock(player, lands);
				});
				sender.sendMessage("§a>>§b自分が持ってる土地の範囲を見やすくしました");
				return true;

			case "invi":
				switch (args[1]) {
					case "add":
						id = Integer.parseInt(args[2]);
						if (!this.main.land.isLand(id)) {
							sender.sendMessage("§a>>§bその土地は存在しません");
							return false;
						}
						land = this.main.land.getLand(id);
						if (!land.isOwner(name)) {
							sender.sendMessage("§a>>§b貴方はその土地の所有者ではありません");
							return false;
						}
						if (!land.isInvite(name)) {
							land.addInvite(args[3].toLowerCase());
							this.main.land.updateLand(land);
							sender.sendMessage("§a>>§b" + args[3] + "を共有に追加しました");
							return true;
						}else{
							sender.sendMessage("§a>>§b" + args[3] + "は既に追加されています");
							return false;
						}

					case "del":
						id = Integer.parseInt(args[2]);
						if (!this.main.land.isLand(id)) {
							sender.sendMessage("§a>>§bその土地は存在しません");
							return false;
						}
						land = this.main.land.getLand(id);
						if (!land.isOwner(name)) {
							sender.sendMessage("§a>>§b貴方はその土地の所有者ではありません");
							return false;
						}
						if (land.isInvite(args[3].toLowerCase())) {
							land.delInvite(args[3].toLowerCase());
							this.main.land.updateLand(land);
							sender.sendMessage("§a>>§b" + args[3] + "共有から外しました");
							return true;
						}else{
							sender.sendMessage("§a>>§b" + args[3] + "は共有されていません");
							return false;
						}

					case "list":
						id = Integer.parseInt(args[2]);
						if (!this.main.land.isLand(id)) {
							sender.sendMessage("§a>>§bその土地は存在しません");
							return false;
						}
						land = this.main.land.getLand(id);
						String[] inv = land.invite.split(":");
						if (inv.length != 0) {
							sender.sendMessage("§a>>§b共有されている人");
							for (int i = 0; i < inv.length; i++) {
								sender.sendMessage("§a>>§b" + inv[i]);
							}
							return true;
						}else{
							sender.sendMessage("§a>>§b共有してる人がいません");
							return false;
						}
					}
				}
		return false;
	}

}
