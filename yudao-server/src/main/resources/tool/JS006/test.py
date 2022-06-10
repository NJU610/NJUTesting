import os
from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
import getopt
import sys
from docx2pdf import convert
import platform
from mailmerge import MailMerge  # 引用邮件处理模块

# -t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\NST－04－JS006－2011－软件测试方案.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\output"

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
  
  #-t "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\NST－04－JS006－2011－软件测试方案.docx" -i "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\data.json" -o "C:\Users\Yongp\Desktop\Classified\NJUTesting\yudao-server\src\main\resources\tool\JS006\output"
  #template = 'C:\\Users\\Yongp\\Desktop\\Classified\\NJUTesting\\yudao-server\\src\\main\\resources\\tool\\JS006\\NST－04－JS006－2011－软件测试方案.docx'  # 模版位置
  with open(i_path, 'r', encoding='utf8') as fp:
    jdata = json.load(fp)
    
    document = MailMerge(template)

    document.merge(
      Label = jdata["1-1 标识"],
      SysOverview = jdata["1-2 系统概述"],
      DocOverview = jdata["1-3 文档概述"],
      Baseline = jdata["基线"],
      File = jdata["引用文件"],
      HardwareEnv = jdata["3-1 硬件"],
      SoftwareEnv = jdata["3-2 软件"],
      OtherEnv = jdata["3-3 其他"],
      Org = jdata["3-4 参与组织"],
      Person = jdata["3-5 人员"],
      OverallDesign = jdata["4-1 总体设计"],
      TestLayer = jdata["4-1-1 测试级别"],
      TestType = jdata["4-1-2 测试类别"],
      TestCon = jdata["4-1-3 一般测试条件"],
      Plan = jdata["4-2 计划执行的测试"],
      Testcase = jdata["4-3 测试用例"],
      Wq1 = jdata["workQuantity1"],
      Wq2 = jdata["workQuantity2"],
      Wq3 = jdata["workQuantity3"],
      Wq4 = jdata["workQuantity4"],
      St1 = jdata["time1"][0],
      St2 = jdata["time2"][0],
      St3 = jdata["time3"][0],
      St4 = jdata["time4"][0],
      En1 = jdata["time1"][1],
      En2 = jdata["time2"][1],
      En3 = jdata["time3"][1],
      En4 = jdata["time4"][1],
      Tracable = jdata["需求的可追踪性"]
    )

    wordname = o_path + '.docx' 
    document.write(wordname)  # 创建新文件
    if platform.system() == "Windows":
          convert(wordname, o_path + '.pdf')
    else:
          os.system("libreoffice --invisible --convert-to pdf --outdir " + o_path[0:o_path.rfind('/')+1]+" "  + o_path + ".docx")
    
if __name__ == '__main__':
    main(sys.argv[1:])
    



      
    


#