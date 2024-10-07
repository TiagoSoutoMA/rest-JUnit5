<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->

<#if data.Timestamp??>
<h3>Timestamp: ${data.timestamp?string("yyyy-MM-dd HH:mm:ss")}</h3>
</#if>

<#if data.body??>
<h3>Body: </h3>
<span>${data.body}</span>
</#if>

<#if data.method??>
<h3>Metodo: </h3>
<span>${data.method}</span>
</#if>

<#if data.url??>
<h3>URI: </h3>
<span>${data.url}</span>
</#if>