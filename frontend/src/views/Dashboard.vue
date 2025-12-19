<template>
  <div class="page-container">
    <a-typography-title :heading="4">仪表盘</a-typography-title>
    <a-space wrap>
      <a-statistic title="用户数量" :value="100" />
      <a-statistic title="题目数量" :value="500" />
      <a-statistic title="试卷数量" :value="20" />
      <a-statistic title="考试数量" :value="15" />
    </a-space>
  </div>
</template>

<script setup>
</script>

<style scoped>
.page-container {
  padding: 16px;
}
</style>


