<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="120px"
    >
      <el-form-item label="连接名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入连接名称" />
      </el-form-item>
      <el-form-item label="主机地址" prop="host">
        <el-input v-model="formData.host" placeholder="请输入主机地址" />
      </el-form-item>
      <el-form-item label="端口" prop="port">
        <el-input-number v-model="formData.port" placeholder="请输入端口" :min="1" :max="65535" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="formData.password" placeholder="请输入密码" type="password" show-password />
      </el-form-item>
      <el-form-item label="数据库索引" prop="database">
        <el-input-number v-model="formData.database" placeholder="请输入数据库索引" :min="0" :max="15" />
      </el-form-item>
      <el-form-item label="连接超时(ms)" prop="timeout">
        <el-input-number v-model="formData.timeout" placeholder="请输入连接超时时间" :min="100" :max="60000" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as RedisConfigApi from '@/api/infra/redisConfig'

defineOptions({ name: 'InfraRedisConfigForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<RedisConfigApi.RedisConfigVO>({
  id: undefined,
  name: '',
  host: '',
  port: 6379,
  password: '',
  database: 0,
  timeout: 5000
})
const formRules = reactive({
  name: [{ required: true, message: '连接名称不能为空', trigger: 'blur' }],
  host: [{ required: true, message: '主机地址不能为空', trigger: 'blur' }],
  port: [{ required: true, message: '端口不能为空', trigger: 'blur' }],
  database: [{ required: true, message: '数据库索引不能为空', trigger: 'blur' }],
  timeout: [{ required: true, message: '连接超时不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await RedisConfigApi.getRedisConfig(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as RedisConfigApi.RedisConfigVO
    if (formType.value === 'create') {
      await RedisConfigApi.createRedisConfig(data)
      message.success(t('common.createSuccess'))
    } else {
      await RedisConfigApi.updateRedisConfig(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: '',
    host: '',
    port: 6379,
    password: '',
    database: 0,
    timeout: 5000
  }
  formRef.value?.resetFields()
}
</script>
