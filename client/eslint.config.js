import { defineConfig } from 'eslint/config';
import globals from 'globals';
import pluginVue from 'eslint-plugin-vue';

export default defineConfig([
  { files: ['**/*.{js,mjs,cjs,vue}'] },
  {
    files: ['**/*.{js,mjs,cjs,vue}'],
    languageOptions: { globals: globals.browser }
  },
  pluginVue.configs['flat/essential']
]);
