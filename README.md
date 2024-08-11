### 下方为中文文档
***

# Search Item In Box Documentation
## Function Overview

This Forge mod allows you to search for items inside nearby chests and is compatible with *JEI*.

- When hovering over an item with the mouse pointer and pressing a specific key (default is **Y**), it will automatically search nearby chests for the item and mark them.
- When installed only on the client-side, it can only search chests that you have previously opened. Data resets upon exiting the game or moving away from the chunk where the chest is located; you need to reopen the chest to record data again.

## Configuration Options

You can configure the behavior and appearance of the mod through commands or configuration files.

### Commands

- Enable whitelist
```plaintext
/sib player_list enable_white_list true
   This command enables the whitelist.
```
- Edit list
1. Add players to the list
    ```plaintext
    /sib player_list list add [players]
    ```
2. Remove players from the li![img.png](img.png)st
    ```plaintext
    /sib player_list list remove [players]
    ```
3. Clear the list
    ```plaintext
    /sib player_list list clear
    ```
4. Show all players in the list
    ```plaintext
    /sib player_list list show
    ```
5. Check current list type: **black_list**/**white_list**
    ```plaintext
    /sib player_list list type
    ```

### Configuration File

- The following options can be set in the configuration file:
- Both Sides
> - Search Distance (*distance*)  
    > When configured on both client and server sides, the maximum search distance cannot exceed the server's search distance.  
    > Default distance is 2 chunks.
- Client-side
> - Particle Display Time (*particle_live_time*)  
    > Time particles are displayed on the client, unit unspecified.
> - Highlighted Slot Time (*slot_height_light_time*)  
    > Duration items are highlighted in slots when found, in ticks.
> - Highlighted Slot Color (*slot_height_light_color*)  
    > Color of highlighted slots where items are found, specified in ARGB converted to decimal, default is -3385513 (0xFFCC5757).

- Server-side
> - Minimum Search Interval (*server_search_interval*)  
    > Minimum interval in milliseconds (ms) for each player's query request.
> - Enable Whitelist (*enable_white_list*)  
    > Whether to enable whitelist on the server, default is blacklist (client-side modifications ineffective).
> - Blacklist (*black_list*)  
    > List of players' UUIDs in the blacklist.
> - Whitelist (*white_list*)  
    > List of players' UUIDs in the whitelist.
> - 

# 中文文档
# 箱子搜索介绍文档
***
## 功能概述

这个Forge模组允许你在附近的箱子内查找物品，并且与*JEI*兼容。

- 当鼠标指针指在某一物品上方时按下特定键位（**默认是Y**），会自动寻找附近箱子内的物品并标记出来。
- 当只有客户端安装时，仅能查找你已打开过的箱子。且退出游戏或远离该箱子所在区块后数据会重置，需重新打开箱子才能再次记录数据。

## 配置选项

你可以通过指令或配置文件来配置模组的行为和外观。

### 指令

- 设置是否启用白名单
```plaintext
/sib player_list enable_white_list true
该指令表示启用白名单
```
- 编辑名单  
1. 向名单中添加玩家
    ```plaintext
    /sib player_list list add [players]
    ```
2. 从名单中移除玩家
    ```plaintext
    /sib player_list list remove [players]
    ```
3. 清空名单
    ```plaintext
    /sib player_list list clear
    ```
4. 展示名单所有玩家
    ```plaintext
    /sib player_list list show
    ```
5. 查看当前名单类型：**black_list**/**white_list**
    ```plaintext
    /sib player_list list type
    ```
### 配置文件
- 在配置文件中可以设置以下选项：
- 双端
> - 查找距离   (*distance*)  
> 当客户端与服务端均配置时，查找距离最大不能超过服务端查找距离  
> 默认距离为2区块
- 客户端
> - 粒子显示时间  (*particle_live_time*)  
> 粒子在客户端的显示时间，单位不清楚是什么
> - 物品栏高亮时间  (*slot_height_light_time*)  
> 被查找的物品所在栏位高亮持续的时间，单位为Tick
> - 物品栏高亮颜色  (*slot_height_light_color*)  
> 被查找物品栏所在栏位高亮颜色，需要设置为ARGB转十进制值，如默认为-3385513(0xFFCC5757)
> 
- 服务端
> - 最小查找间隔  (*server_search_interval*)  
> 每个玩家最小请求查询的时间间隔，单位为毫秒(ms)
> - 是否启用白名单  (*enable_white_list*)  
> 是否在服务端启用白名单，默认为黑名单，客户端修改无效
> - 黑名单  (*black_list*)  
> 黑名单列表，存储玩家的uuid
> - 白名单  (*white_list*)  
> 白名单列表，存储玩家的uuid
> 