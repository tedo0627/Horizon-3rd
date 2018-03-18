package tedo.WorldEdit;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import tedo.WorldEdit.command.BlockConvertCommand;
import tedo.WorldEdit.command.CopyCommand;
import tedo.WorldEdit.command.CutCommand;
import tedo.WorldEdit.command.CylCommand;
import tedo.WorldEdit.command.DirectlyCopyCommand;
import tedo.WorldEdit.command.DirectlySetCommand;
import tedo.WorldEdit.command.HcylCommand;
import tedo.WorldEdit.command.HsphereCommand;
import tedo.WorldEdit.command.PasteCommand;
import tedo.WorldEdit.command.RandomCommand;
import tedo.WorldEdit.command.ReplaceCommand;
import tedo.WorldEdit.command.RotationCommand;
import tedo.WorldEdit.command.SetCommand;
import tedo.WorldEdit.command.SphereCommand;
import tedo.WorldEdit.command.UndoCommand;

public class MainCommand extends Command{

	public WorldEdit main;

	public MainCommand(WorldEdit main) {
		super("we", "地形を操作するコマンド");

		this.setPermission("op");

//		this.getCommandParameters().clear();
//
//		this.addCommandParameters("default", new CommandParameter[]{
//			new CommandParameter("cyl", CommandParameter.ARG_TYPE_STRING, false),
//			new CommandParameter("ブロックID", CommandParameter.ARG_TYPE_INT, false),
//			new CommandParameter("半径", CommandParameter.ARG_TYPE_INT, false),
//			new CommandParameter("高さ", CommandParameter.ARG_TYPE_INT, false),
//		});
//
//		this.addCommandParameters("default", new CommandParameter[]{
//			new CommandParameter("set", false, new String[]{"set"}),
//			new CommandParameter("ブロックID", false, CommandParameter.ENUM_TYPE_BLOCK_LIST)
//		});
//		this.addCommandParameters("set2", new CommandParameter[]{
//				new CommandParameter("set", false, new String[]{"set"}),
//				new CommandParameter("ブロックID", CommandParameter.ARG_TYPE_INT, false)
//		});
//		this.addCommandParameters("set3", new CommandParameter[]{
//				new CommandParameter("set", false, new String[]{"set"}),
//				new CommandParameter("ブロックID:META", CommandParameter.ARG_TYPE_STRING, false)
//		});
//
//		this.addCommandParameters("cut", new CommandParameter[]{
//				new CommandParameter("cut", new String[]{"cut"}),
//		});

		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§a>>§bコンソールから実行できません");
			return false;
		}
		if (args.length < 1) {
			sender.sendMessage("§a>>§b// set [ID]");
			sender.sendMessage("§a>>§b// cut");
			sender.sendMessage("§a>>§b// undo");
			return false;
		}
		Player player = (Player) sender;
		switch (args[0]) {
			case "set":
				SetCommand.execution(player, args, this.main);
			break;

			case "cut":
				CutCommand.execution(player, this.main);
			break;

			case "random":
				RandomCommand.execution(player, args, this.main);
			break;

			case "undo":
				UndoCommand.execution(player, this.main);
			break;

			case "copy":
				CopyCommand.execution(player, this.main);
			break;

			case "paste":
				PasteCommand.execution(player, this.main);
			break;

			case "replace":
				ReplaceCommand.execution(player, args, this.main);
			break;

			case "cyl":
				CylCommand.execution(player, args, this.main);
			break;

			case "hcyl":
				HcylCommand.execution(player, args, this.main);
			break;

			case "sphere":
				SphereCommand.execution(player, args, this.main);
			break;

			case "hsphere":
				HsphereCommand.execution(player, args, this.main);
			break;

			case "convert":
				BlockConvertCommand.execution(player, this.main);
			break;

			case "rotation":
				RotationCommand.execution(player, this.main);
			break;

			case "dcopy":
				DirectlyCopyCommand.execution(player, this.main);
			break;

			case "dset":
				DirectlySetCommand.execution(player, this.main);
			break;

			default:
				String txt1 = "";
				for (String cmd : args) {
					txt1 = txt1 + cmd + " ";
				}
				sender.sendMessage(txt1);
			break;
		}
		return false;
	}

}
