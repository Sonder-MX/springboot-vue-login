<template>
  <div>
    <router-view />
  </div>
</template>

<script setup>
import { useDark, useToggle } from '@vueuse/core'

useDark({
  selector: 'html',
  attribute: 'class',
  valueDark: 'dark',
  valueLight: 'light',
})

useDark({
  onChanged: (value) => {
    useToggle(value)
  },
})
</script>

<style scoped></style>
