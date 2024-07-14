package cn.xzhao.search_in_box.command;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.SIB_MOD;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class PlayerListCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("sib");
        root.then(initPlayerListCommand());

        dispatcher.register(root);
    }
    private static LiteralArgumentBuilder<CommandSourceStack> initPlayerListCommand(){
        LiteralArgumentBuilder<CommandSourceStack> cmd= Commands.literal("player_list")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4));
        addChangeListTypeList(cmd);
        addPlayerListEditCommand(cmd);
        return cmd;
    }
    private static void addChangeListTypeList(LiteralArgumentBuilder<CommandSourceStack> root){
         root.then(
                Commands.literal("enable_white_list")
                        .then(
                                Commands.argument("enableWhiteList", BoolArgumentType.bool())
                                        .executes(
                                                context->{
                                                    boolean enable=BoolArgumentType.getBool(context,"enableWhiteList");
                                                    if(Config.changeEnableList(enable)){
                                                        context.getSource().sendSystemMessage(
                                                                Component.translatable(String.format("message.command.%s.change.success", SIB_MOD.MODID),
                                                                        enable));
                                                    }else {
                                                        context.getSource().sendSystemMessage(
                                                                Component.translatable(String.format("message.command.%s.change.fail", SIB_MOD.MODID),
                                                                        enable));
                                                    }
                                                    return 1;
                                                })
                        )
        );
    }
    private static void addPlayerListEditCommand(LiteralArgumentBuilder<CommandSourceStack> root) {
        root.then(
            Commands.literal("list")
                    .then(
                            Commands.literal("add")
                                    .then(
                                            Commands.argument("players", EntityArgument.players())
                                            .executes(
                                                    context->{
                                                        Collection<ServerPlayer> players= EntityArgument.getPlayers(context,"players");
                                                        CommandSourceStack source = context.getSource();
                                                        String uuid;
                                                        for(ServerPlayer player:players){
                                                            uuid=player.getStringUUID();
                                                            if(Config.playerList.add(uuid)){
                                                                source.sendSystemMessage(
                                                                        Component.translatable(
                                                                                String.format("message.command.%s.list.add.success", SIB_MOD.MODID),
                                                                                    player.getDisplayName()
                                                                                )
                                                                );
                                                            }else{
                                                                source.sendSystemMessage(
                                                                        Component.translatable(
                                                                                String.format("message.command.%s.list.add.fail", SIB_MOD.MODID),
                                                                                player.getDisplayName()
                                                                        )
                                                                );
                                                            }
                                                        }
                                                        Config.saveCurrentList(Arrays.asList(Config.playerList.toArray()));
                                                        return 1;
                                                    }
                                            )
                                    )
                    )
                    .then(
                            Commands.literal("remove")
                                    .then(
                                            Commands.argument("players", EntityArgument.players())
                                                    .executes(
                                                            context->{
                                                                Collection<ServerPlayer> players= EntityArgument.getPlayers(context,"players");
                                                                CommandSourceStack source = context.getSource();
                                                                String uuid;
                                                                for(ServerPlayer player:players){
                                                                    uuid=player.getStringUUID();
                                                                    if(Config.playerList.remove(uuid)){
                                                                        source.sendSystemMessage(
                                                                                Component.translatable(
                                                                                        String.format("message.command.%s.list.remove.success", SIB_MOD.MODID),
                                                                                        player.getDisplayName()
                                                                                )
                                                                        );
                                                                    }else{
                                                                        source.sendSystemMessage(
                                                                                Component.translatable(
                                                                                        String.format("message.command.%s.list.remove.fail", SIB_MOD.MODID),
                                                                                        player.getDisplayName()
                                                                                )
                                                                        );
                                                                    }
                                                                }
                                                                Config.saveCurrentList(Arrays.asList(Config.playerList.toArray()));
                                                                return 1;
                                                            }
                                                    )
                                    )
                    )
                    .then(
                            Commands.literal("clear")
                                    .executes(
                                            context->{
                                                Config.playerList.clear();
                                                Config.saveCurrentList(new LinkedList<>());
                                                return 1;
                                            }
                                    )
                    )
                    .then(
                            Commands.literal("show")
                                    .executes(
                                            context->{
                                                StringBuffer sb=new StringBuffer();
                                                if(Config.playerList.isEmpty()){
                                                    context.getSource().sendSystemMessage(
                                                            Component.translatable(String.format("message.command.%s.list.show", SIB_MOD.MODID))
                                                    );
                                                }else{
                                                    for(String uuid:Config.playerList){
                                                        sb.append(uuid).append("\n");
                                                    }
                                                    context.getSource().sendSystemMessage(Component.literal(sb.toString()));
                                                }

                                                return 1;
                                            }
                                    )
                    )
                    .then(
                            Commands.literal("type")
                                    .executes(
                                            context->{
                                                context.getSource().sendSystemMessage(
                                                        Component.literal(Config.isEnableWhiteList()?"white_list":"black_list")
                                                );
                                                return 1;
                                            }
                                    )
                    )
        );
    }
}
