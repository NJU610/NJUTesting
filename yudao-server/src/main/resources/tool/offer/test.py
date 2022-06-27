from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
import platform
import os
from mailmerge import MailMerge  # 引用邮件处理模块

#-t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\offer\报价单.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\offer\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\offer\output"

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
    document = MailMerge(template)

    _Project1 = ""
    _Item1 = ""
    _Price1 = ""
    _Brief1 = ""
    _LTotal1 = ""
    _Project2 = ""
    _Item2 = ""
    _Price2 = ""
    _Brief2 = ""
    _LTotal2 = ""
    if len(jdata["项目表格"]) >= 1:
      _Project1 = jdata["项目表格"][0]["xiangmu"]
      _Item1 = jdata["项目表格"][0]["fenxiang"]
      _Price1 = jdata["项目表格"][0]["danjia"]
      _Brief1 = jdata["项目表格"][0]["shuoming"]
      _LTotal1 = jdata["项目表格"][0]["hangheji"]
    if len(jdata["项目表格"]) >= 2:
      _Project2 = jdata["项目表格"][1]["xiangmu"]
      _Item2 = jdata["项目表格"][1]["fenxiang"]
      _Price2 = jdata["项目表格"][1]["danjia"]
      _Brief2 = jdata["项目表格"][1]["shuoming"]
      _LTotal2 = jdata["项目表格"][1]["hangheji"], 
  
    
    document.merge(
      
      SoftName = jdata["软件名称"],
      Provider = jdata["报价提供人"],
      Date = jdata["报价日期"],
      StartDate = jdata["报价有效期"][0],
      EndDate = jdata["报价有效期"][1],
      Subtotal = jdata["小计"],
      Total = jdata["总计"],
      Tax = jdata["税率（8%）"],
      Sig = jdata["sign"],
      
      
      Project1 = _Project1,
      Item1 = _Item1,
      Price1 = _Price1,
      Brief1 = _Brief1,
      LTotal1 = _LTotal1,
      Project2 = _Project2,
      Item2 = _Item2,
      Price2 = _Price2,
      Brief2 = _Brief2,
      LTotal2 = _LTotal2
    )
    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")

if __name__ == '__main__':
    main(sys.argv[1:])
      
    
