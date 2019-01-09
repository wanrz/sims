<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据打印</title>
<style type="text/css">
    body {
        background: white;
        margin: 0px;
        padding: 0px;
        font-size: 13px;
        text-align: left;
    }

    .pb {
        font-size: 13px;
        border-collapse: collapse;
    }

    .pb th {
        font-weight: bold;
        text-align: center;
        border: 1px solid #333333;
        padding: 2px;
    }

    .pb td {
        border: 1px solid #333333;
        padding: 2px;
    }
</style>
</head>
<body>
    <script type="text/javascript">
        document.write(window.dialogArguments);
        window.print();
    </script>
</body>
</html>