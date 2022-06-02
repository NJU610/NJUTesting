import json
import xlrd
import xlwt

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js009\\data.json", 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    data = jdata["测试用例"]
    xl = xlwt.Workbook(encoding='utf-8')
    sheet = xl.add_sheet("测试用例",cell_overwrite_ok=False)
    style = xlwt.XFStyle()
    font = xlwt.Font()
    font.bold = True
    style.font = font
    
    sheet.write(0, 0, "测试分类", style)
    sheet.write(0, 1, "序号", style)
    sheet.write(0, 2, "测试用例设计说明", style)
    sheet.write(0, 3, "与本测试用例有关的规约说明", style)
    sheet.write(0, 4, "前提条件", style)
    sheet.write(0, 5, "测试用例执行过程", style)
    sheet.write(0, 6, "预期的结果", style)
    sheet.write(0, 7, "测试用例设计者", style)
    sheet.write(0, 8, "实际结果", style)
    sheet.write(0, 9, "是否与预期结果一致", style)
    sheet.write(0, 10, "相关的BUG编号", style)
    sheet.write(0, 11, "用例执行者", style)
    sheet.write(0, 12, "执行测试时间", style)
    sheet.write(0, 13, "确认人", style)

    cnt = 0
    for i in data:
        cnt += 1
        sheet.write(cnt, 0, i["classification"])
        sheet.write(cnt, 1, i["tid"])
        sheet.write(cnt, 2, i["design"])
        sheet.write(cnt, 3, i["statute"])
        sheet.write(cnt, 4, i["preconditions"])
        sheet.write(cnt, 5, i["result"])
        sheet.write(cnt, 6, i["author"])
        sheet.write(cnt, 7, i["reality"])
        sheet.write(cnt, 8, i["accord"])
        sheet.write(cnt, 9, i["bugid"])
        sheet.write(cnt, 10, i["executor"])
        sheet.write(cnt, 11, i["time"])
        sheet.write(cnt, 12, i["confirmor"])

    
    xl.save("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js009\\output.xls")
    
    