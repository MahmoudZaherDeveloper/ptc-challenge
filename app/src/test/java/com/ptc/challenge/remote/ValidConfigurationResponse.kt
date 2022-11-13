package com.ptc.challenge.remote

val validConfigurationResponse = """
   {
  "success": true,
  "session": {
    "id": "75h2uecflq49ohiet1nkdpj9n1",
    "expire": null,
    "YII_CSRF_TOKEN": "c5f9d473938963251461901ad4cece609a5e04c3"
  },
  "metadata": {
    "currency": {
      "iso": "NGN",
      "currency_symbol": "₦",
      "position": 0,
      "decimals": 0,
      "thousands_sep": ",",
      "decimals_sep": "."
    },
    "languages": [
      {
        "code": "en_NG",
        "name": "English",
        "default": true
      }
    ],
    "support": {
      "phone_number": "123456789",
      "call_to_order_enabled": false,
      "cs_email": "test@jumia.com.ng"
    }
  }
}
""".trimIndent()