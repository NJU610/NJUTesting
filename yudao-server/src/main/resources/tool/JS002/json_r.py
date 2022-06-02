import json
import xlrd
import xlwt

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js002\\data.json", 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    
    workbook = xlwt.Workbook(encoding='utf8')
    worksheet = workbook.add_sheet("Sheet1")
    
    worksheet.write()
    