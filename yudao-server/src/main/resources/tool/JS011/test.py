import json
import xlrd
import xlwt
import getopt
import sys

def main(argv):
      
  o_path = ""
  i_path = ""
  template = ""
  
  try:
    opts, args = getopt.getopt(argv,'o:i:t:',["ofile=","ifile=","template="])
  except getopt.GetoptError:
    sys.exit(2)
    
  for opt, arg in opts:
      if opt in ("-o", "--ofile"):
          o_path = arg
      elif opt in ("-i", "--ifile"):
          i_path = arg 
      elif opt in ("-t", "--template"):
          template = arg
          
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    data = jdata["测试用例"]
    xl = xlwt.Workbook(encoding='utf-8')
    sheet = xl.add_sheet("测试用例",cell_overwrite_ok=False)
    style = xlwt.XFStyle()
    font = xlwt.Font()
    font.bold = True
    style.font = font
    
    sheet.write(0, 0, "序号", style)
    sheet.write(0, 1, "问题（缺陷）简要描述", style)
    sheet.write(0, 2, "对应需求条目", style)
    sheet.write(0, 3, "发现缺陷的初始条件", style)
    sheet.write(0, 4, "发现缺陷用例及具体操作路径（要具体）", style)
    sheet.write(0, 5, "关联用例", style)
    sheet.write(0, 6, "发现时间", style)
    sheet.write(0, 7, "责任人", style)
    sheet.write(0, 8, "修改建议", style)

    cnt = 0
    for i in data:
        cnt += 1
        sheet.write(cnt, 0, i["qid"])
        sheet.write(cnt, 1, i["desc"])
        sheet.write(cnt, 2, i["requirementitem"])
        sheet.write(cnt, 3, i["initialconditions"])
        sheet.write(cnt, 4, i["defectcases"])
        sheet.write(cnt, 5, i["associatedcases"])
        sheet.write(cnt, 6, i["time"])
        sheet.write(cnt, 7, i["responsible"])
        sheet.write(cnt, 8, i["suggest"])

    xl.save(o_path + ".xls")

if __name__ == '__main__':
    main(sys.argv[1:])
    