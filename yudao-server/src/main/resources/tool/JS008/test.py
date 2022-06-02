import json
import xlrd
import xlwt

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js008\\data.json", 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    data = jdata["测试用例"]
    xl = xlwt.Workbook(encoding='utf-8')
    sheet = xl.add_sheet("测试用例",cell_overwrite_ok=False)
    style = xlwt.XFStyle()
    font = xlwt.Font()
    font.bold = True
    style.font = font
    
    sheet.write(0, 0, "测试分类", style)
    sheet.write(0, 1, "ID", style)
    sheet.write(0, 2, "测试用例设计说明", style)
    sheet.write(0, 3, "与本测试用例有关的规约说明", style)
    sheet.write(0, 4, "预期的结果", style)
    sheet.write(0, 5, "测试用例设计者", style)
    sheet.write(0, 6, "测试时间", style)

    cnt = 0
    for i in data:
        cnt += 1
        sheet.write(cnt, 0, i["classification"])
        sheet.write(cnt, 1, i["tid"])
        sheet.write(cnt, 2, i["design"])
        sheet.write(cnt, 3, i["statute"])
        sheet.write(cnt, 4, i["result"])
        sheet.write(cnt, 5, i["author"])
        sheet.write(cnt, 6, i["time"])
    
    xl.save("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js008\\output.xls")
    
    