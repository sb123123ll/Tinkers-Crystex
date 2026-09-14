import os
import json
import shutil
from PIL import Image

# 基础路径
assets_dir = r"G:\myfirstmod\1.12.2\Tinkers' Crystex\src\main\resources\assets\tinkerscrystex"

blockstates_dir = os.path.join(assets_dir, "blockstates")
models_block_dir = os.path.join(assets_dir, "models", "block")
models_item_dir = os.path.join(assets_dir, "models", "item")
textures_block_dir = os.path.join(assets_dir, "textures", "blocks")
textures_item_dir = os.path.join(assets_dir, "textures", "items")
textures_fluid_dir = os.path.join(textures_block_dir, "fluids")
lang_file = os.path.join(assets_dir, "lang", "zh_cn.lang")

# 1. 生成 Blockstates
blockstate_data = {
    "forge_marker": 1,
    "variants": {
        "normal": {"model": "tinkerscrystex:pyroclast_crystal_block"}
    }
}
with open(os.path.join(blockstates_dir, "pyroclast_crystal_block.json"), "w") as f:
    json.dump(blockstate_data, f, indent=4)

fluid_blockstate = {
    "forge_marker": 1,
    "variants": {
        "fluid": {
            "model": "forge:fluid",
            "custom": { "fluid": "pyroclast_crystal" }
        }
    }
}
with open(os.path.join(blockstates_dir, "fluid_pyroclast_crystal.json"), "w") as f:
    json.dump(fluid_blockstate, f, indent=4)

# 2. 生成 Models
model_block_data = {
    "parent": "block/cube_all",
    "textures": {
        "all": "tinkerscrystex:blocks/pyroclast_crystal_block"
    }
}
with open(os.path.join(models_block_dir, "pyroclast_crystal_block.json"), "w") as f:
    json.dump(model_block_data, f, indent=4)

model_item_block_data = {
    "parent": "tinkerscrystex:block/pyroclast_crystal_block"
}
with open(os.path.join(models_item_dir, "pyroclast_crystal_block.json"), "w") as f:
    json.dump(model_item_block_data, f, indent=4)

model_item_ingot_data = {
    "parent": "item/generated",
    "textures": {
        "layer0": "tinkerscrystex:items/pyroclast_crystal"
    }
}
with open(os.path.join(models_item_dir, "pyroclast_crystal.json"), "w") as f:
    json.dump(model_item_ingot_data, f, indent=4)

model_item_nugget_data = {
    "parent": "item/generated",
    "textures": {
        "layer0": "tinkerscrystex:items/pyroclast_crystal_nugget"
    }
}
with open(os.path.join(models_item_dir, "pyroclast_crystal_nugget.json"), "w") as f:
    json.dump(model_item_nugget_data, f, indent=4)

# 3. 语言文件更新
with open(lang_file, "a", encoding="utf-8") as f:
    f.write("\n")
    f.write("item.tinkerscrystex.pyroclast_crystal.name=烬晶\n")
    f.write("item.tinkerscrystex.pyroclast_crystal_nugget.name=烬晶粒\n")
    f.write("tile.tinkerscrystex.pyroclast_crystal_block.name=烬晶块\n")
    f.write("fluid.pyroclast_crystal=熔融烬晶\n")
    f.write("material.pyroclast_crystal.name=烬晶\n")
    f.write("modifier.detonate.name=爆燃\n")
    f.write("modifier.detonate.desc=§6爆燃\n攻击时有几率引发小型爆炸，造成范围伤害（会对自己造成少量伤害）。\n")
    f.write("modifier.unstable.name=不稳定\n")
    f.write("modifier.unstable.desc=§c不稳定\n工具耐久极低时，挖掘速度和攻击力显著提升，但每次使用都有几率额外消耗耐久。\n")

print("Files generated successfully.")
