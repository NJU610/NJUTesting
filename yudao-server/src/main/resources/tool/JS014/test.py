from signal import SIGABRT
from sqlite3 import DatabaseError
import xlrd  # 引入Excel读取模块
import json
import xlwt
from docx2pdf import convert
from mailmerge import MailMerge  # 引用邮件处理模块

# datafile_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js002\\js002.xlsx'  # 表格位置
# data = xlrd.open_workbook(datafile_path)
# table = data.sheet_by_name('这是一个合同')  # excel表名
template = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js014\\NST－04－JS014－2011 - 软件文档评审表.docx'  # 模版位置
# nrows = table.nrows

with open("C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js014\\data.json", 'r', encoding='utf8') as fp:
  jdata = json.load(fp)
  document = MailMerge(template)

  document.merge(
    SoftwareName = jdata["softName"],
    SoftwareVer = jdata["version"],
    Delegation = jdata["organization"],
    Viewer = jdata["reviewer"],
    checker = jdata["checker"],
    
    Res1 = jdata["dataSource"][0]["result"],
    Res2 = jdata["dataSource"][1]["result"],
    Res3 = jdata["dataSource"][2]["result"],
    Res4 = jdata["dataSource"][3]["result"],
    Res5 = jdata["dataSource"][4]["result"],
    Res6 = jdata["dataSource"][5]["result"],
    Res7 = jdata["dataSource"][6]["result"],
    Res8 = jdata["dataSource"][7]["result"],
    Res9 = jdata["dataSource"][8]["result"],
    Res10 = jdata["dataSource"][9]["result"],
    Res11 = jdata["dataSource"][10]["result"],
    Res12 = jdata["dataSource"][11]["result"],
    Res13 = jdata["dataSource"][12]["result"],
    Res14 = jdata["dataSource"][13]["result"],

    Exp1 = jdata["dataSource"][0]["explain"],
    Exp2 = jdata["dataSource"][1]["explain"],
    Exp3 = jdata["dataSource"][2]["explain"],
    Exp4 = jdata["dataSource"][3]["explain"],
    Exp5 = jdata["dataSource"][4]["explain"],
    Exp6 = jdata["dataSource"][5]["explain"],
    Exp7 = jdata["dataSource"][6]["explain"],
    Exp8 = jdata["dataSource"][7]["explain"],
    Exp9 = jdata["dataSource"][8]["explain"],
    Exp10 = jdata["dataSource"][9]["explain"],
    Exp11 = jdata["dataSource"][10]["explain"],
    Exp12 = jdata["dataSource"][11]["explain"],
    Exp13 = jdata["dataSource"][12]["explain"],
    Exp14 = jdata["dataSource"][13]["explain"],

    Res15 = jdata["documentReviewData"][0]["result"],
    Res16 = jdata["documentReviewData"][1]["result"],
    Res17 = jdata["documentReviewData"][2]["result"],
    Res18 = jdata["documentReviewData"][3]["result"],
    Res19 = jdata["documentReviewData"][4]["result"],
    Res20 = jdata["documentReviewData"][5]["result"],
    Res21 = jdata["documentReviewData"][6]["result"],
    Res22 = jdata["documentReviewData"][7]["result"],
    Res23 = jdata["documentReviewData"][8]["result"],
    Res24 = jdata["documentReviewData"][9]["result"],
    Res25 = jdata["documentReviewData"][10]["result"],
    Res26 = jdata["documentReviewData"][11]["result"],
    Res27 = jdata["documentReviewData"][12]["result"],
    Res28 = jdata["documentReviewData"][13]["result"],
    Res29 = jdata["documentReviewData"][14]["result"],
    Res30 = jdata["documentReviewData"][15]["result"],
    Res31 = jdata["documentReviewData"][16]["result"],
    Res32 = jdata["documentReviewData"][17]["result"],
    Res33 = jdata["documentReviewData"][18]["result"],
    Res34 = jdata["documentReviewData"][19]["result"],
    
    Exp15 = jdata["documentReviewData"][0]["explain"],
    Exp16 = jdata["documentReviewData"][1]["explain"],
    Exp17 = jdata["documentReviewData"][2]["explain"],
    Exp18 = jdata["documentReviewData"][3]["explain"],
    Exp19 = jdata["documentReviewData"][4]["explain"],
    Exp20 = jdata["documentReviewData"][5]["explain"],
    Exp21 = jdata["documentReviewData"][6]["explain"],
    Exp22 = jdata["documentReviewData"][7]["explain"],
    Exp23 = jdata["documentReviewData"][8]["explain"],
    Exp24 = jdata["documentReviewData"][9]["explain"],
    Exp25 = jdata["documentReviewData"][10]["explain"],
    Exp26 = jdata["documentReviewData"][11]["explain"],
    Exp27 = jdata["documentReviewData"][12]["explain"],
    Exp28 = jdata["documentReviewData"][13]["explain"],
    Exp29 = jdata["documentReviewData"][14]["explain"],
    Exp30 = jdata["documentReviewData"][15]["explain"],
    Exp31 = jdata["documentReviewData"][16]["explain"],
    Exp32 = jdata["documentReviewData"][17]["explain"],
    Exp33 = jdata["documentReviewData"][18]["explain"],
    Exp34 = jdata["documentReviewData"][19]["explain"],
  )
  o_path = 'C:\\Users\\Yongp\\Desktop\\Classified\\contract_word\\js014\\' #输出地址
  o_name = jdata["_id"] + "_" + "JS014"
  wordname = o_path + o_name + '.docx' 
  document.write(wordname)  # 创建新文件
  convert(wordname, o_path + o_name + '.pdf')
      
    
