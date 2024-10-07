<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->

<#if data??>
<h3>Status: <#if data.responseCode??>${data.responseCode} <#else>Unknown</#if></h3>

<#if data.body??>
<div>
<h3>Response Body</h3>
<pre class="preformated-text">${data.body}</pre>
        </div>
    <#else>
        <div>No Body Content Available</div>
    </#if>
<#else>
    <div>No Data Available</div>
</#if>
