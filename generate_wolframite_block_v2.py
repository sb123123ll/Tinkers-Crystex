from PIL import Image

# 创建 16x16 图像
img = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
pixels = img.load()

# ==========================================
# 提取与精校调色盘 (精确对应 ingot / ore 的黑橙双色主题)
# ==========================================

# 1. 黑钨基底钢 (Dark Wolfram Steel - 对应 ingot 底部和边缘的暗色)
W_HI_CORNER = (85, 88, 98, 255)    # 边框极亮受光角点
W_HI        = (68, 70, 78, 255)    # 外框顶部与左侧受光边
W_MID_HI    = (54, 55, 62, 255)    # 外框中间明度
W_MID       = (42, 43, 48, 255)    # 黑钨钢基色 (~#2a2a30)
W_SHADOW    = (28, 28, 33, 255)    # 外框背光面与阴影
W_DARKEST   = (16, 16, 20, 255)    # 最深凹槽与阴影角点 (#121217)

# 2. 炽橙精炼合金内板 (Refined Wolfram Orange Alloy - 对应 ingot / nugget #e66400 与 #ffb432)
O_PEAK      = (255, 230, 130, 255) # 极亮反光点
O_HI        = (255, 180, 50, 255)  # 亮金橙反光带 (对应 ingot #ffb432)
O_MID_HI    = (242, 140, 20, 255)  # 明亮橙色
O_BASE      = (230, 100, 0, 255)   # 核心基准橙 (对应 ingot #e66400)
O_MID_DARK  = (195, 75, 0, 255)    # 渐变过渡深橙
O_SHADOW    = (150, 48, 0, 255)    # 内板背光阴影
O_GROOVE    = (95, 28, 0, 255)     # 橙色与黑钨交界缝隙线

# ==========================================
# 结构设计：
# 经典 1.12.2 工业重金属合金块结构 (Framed Alloy Block)
# - 外框 (0..15)：坚固的黑钨钢冲压厚外框 (带立体倒角光影)
# - 嵌槽 (2..13)：黑钨钢与内板之间的冲压结构槽
# - 核心内板 (3..12)：浓缩提纯的炽橙色合金冲压面板 (精炼拉丝与高光)
# ==========================================

# 1. 填充整个底板 (黑钨钢基底)
for y in range(16):
    for x in range(16):
        pixels[x, y] = W_MID

# 2. 绘制外框立体倒角 (x, y in 0, 15)
for i in range(1, 15):
    pixels[i, 0] = W_HI
    pixels[0, i] = W_HI
    pixels[i, 15] = W_SHADOW
    pixels[15, i] = W_SHADOW

pixels[0, 0] = W_HI_CORNER
pixels[15, 0] = W_MID_HI
pixels[0, 15] = W_MID_HI
pixels[15, 15] = W_DARKEST

# 第二层黑钨钢缓冲框 (x, y in 1, 14)
for i in range(1, 15):
    pixels[i, 1] = W_MID_HI
    pixels[1, i] = W_MID_HI
    pixels[i, 14] = W_SHADOW
    pixels[14, i] = W_SHADOW
pixels[14, 1] = W_MID
pixels[1, 14] = W_MID
pixels[1, 1] = W_HI
pixels[14, 14] = W_DARKEST

# 3. 冲压凹槽与黑钨固定槽 (x, y in 2, 13)
for i in range(2, 14):
    pixels[i, 2] = W_DARKEST
    pixels[2, i] = W_DARKEST
    pixels[i, 13] = W_DARKEST
    pixels[13, i] = W_DARKEST

# 4. 炽橙合金核心面板 (x, y in 3..12)
orange_pattern = [
    # y = 3
    [O_PEAK, O_HI,    O_HI,   O_HI,   O_MID_HI, O_MID_HI, O_MID_HI, O_BASE,   O_BASE,   O_MID_DARK],
    # y = 4
    [O_HI,   O_HI,    O_HI,   O_MID_HI, O_MID_HI, O_MID_HI, O_BASE,   O_BASE,   O_MID_DARK, O_SHADOW],
    # y = 5
    [O_HI,   O_HI,    O_MID_HI, O_MID_HI, O_BASE,   O_BASE,   O_BASE,   O_MID_DARK, O_SHADOW, O_SHADOW],
    # y = 6
    [O_HI,   O_MID_HI, O_MID_HI, O_BASE,   O_BASE,   O_BASE,   O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW],
    # y = 7
    [O_MID_HI, O_MID_HI, O_BASE, O_BASE,   O_BASE,   O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE],
    # y = 8
    [O_MID_HI, O_BASE,   O_BASE, O_BASE,   O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE, O_GROOVE],
    # y = 9
    [O_BASE,   O_BASE,   O_BASE, O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE, O_GROOVE, O_GROOVE],
    # y = 10
    [O_BASE,   O_BASE,   O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE],
    # y = 11
    [O_MID_DARK, O_MID_DARK, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE],
    # y = 12
    [O_SHADOW, O_SHADOW, O_SHADOW, O_SHADOW, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE, O_GROOVE],
]

for row_idx, row in enumerate(orange_pattern):
    y = 3 + row_idx
    for col_idx, color in enumerate(row):
        x = 3 + col_idx
        pixels[x, y] = color

# 5. 添加黑钨钢强化铆钉/角件 (Reinforced Corner Rivets)
rivets = [
    (3, 3), (12, 3),
    (3, 12), (12, 12)
]
for rx, ry in rivets:
    pixels[rx, ry] = W_DARKEST

# 6. 微调内部金属光泽反射线（拉丝高光）
brush_highlights = [
    (5, 4, O_PEAK), (6, 4, O_HI), (8, 5, O_MID_HI),
    (4, 7, O_HI), (5, 7, O_MID_HI), (9, 7, O_BASE),
    (6, 9, O_MID_HI), (7, 9, O_BASE), (10, 8, O_MID_DARK),
    (4, 11, O_BASE), (5, 11, O_MID_DARK), (8, 10, O_SHADOW)
]
for bx, by, bc in brush_highlights:
    pixels[bx, by] = bc

# 保存
target_path = r"g:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex\textures\blocks\wolframite_block.png"
img.save(target_path)
print("Updated wolframite_block.png with black-orange alloy theme successfully.")
