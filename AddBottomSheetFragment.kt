package com.presentation.bottomsheet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import com.data.models.AddMoneyRequest
import com.data.models.AppliedPromocodeData
import com.data.models.PromocodeDataListData
import com.data.models.UserData
import com.databinding.BottomsheetAddMoneyBinding
import com.presentation.profile.PromoCodeActivity
import com.presentation.utility.PrefKeys
import com.presentation.utility.gone
import com.presentation.utility.setSafeOnClickListener
import com.presentation.utility.showToastError
import com.presentation.utility.visible
import com.google.gson.Gson
import java.lang.Exception


class AddMoneyBottomSheetFragment : BaseBottomSheetDialogFragment() {
    override fun isTitleAvailable() = false

    private lateinit var binding: BottomsheetAddMoneyBinding

    private lateinit var onSelected: (addMoneyRequest: AddMoneyRequest, bottomSheet: AddMoneyBottomSheetFragment) -> Unit

    private var appliedPromocode: AppliedPromocodeData? = null
    private var bottomSheet: AddMoneyBottomSheetFragment? = null

    private var addMoney = ""

    private var userData: UserData? = null

    private var isTextEdited = false

    var launcher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            try {
                var data = it.data
                if (data != null) {
                    val promoCode = data.getStringExtra("promo_data") ?: ""
                    appliedPromocode = Gson().fromJson(promoCode, AppliedPromocodeData::class.java)
                    binding.appliedPromoCodeCL.visible()
                    binding.textViewPromocode.gone()
                    setPromocodeData(appliedPromocode!!)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun setPromocodeData(appliedPromocode: AppliedPromocodeData) {
        binding.textViewPromocodeTitle.text = appliedPromocode.code
        binding.textViewPromocodeDesc.text = "(${appliedPromocode.title})"
    }

    companion object {
        fun showDialog(
            fragmentManager: FragmentManager,
            userData: UserData,
            callback: (addMoneyRequest: AddMoneyRequest, bottomSheet: AddMoneyBottomSheetFragment) -> Unit
        ) {
            val bottomSheet = AddMoneyBottomSheetFragment()
            bottomSheet.bottomSheet = bottomSheet
            bottomSheet.onSelected = callback
            bottomSheet.userData = userData
            bottomSheet.show(fragmentManager, AddMoneyBottomSheetFragment::class.java.simpleName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetAddMoneyBinding.inflate(inflater, container, false)


//        val button100 = binding.findViewById<AppCompatButton>(R.id.button100)
//        val button500 = binding.findViewById<AppCompatButton>(R.id.button500)
//        val button999 = binding.findViewById<AppCompatButton>(R.id.button999)
//        val editTextAmount = binding.findViewById<AppCompatEditText>(R.id.editTextAmount)


      
        binding.textViewBalance.text = "₹ ${userData?.usable_coin_balance}"

        binding.button100.setOnClickListener {
            binding.editTextAmount.isFocusable = false
            binding.button500.isChecked = false
            binding.button999.isChecked = false
            binding.button100.isChecked = true
            binding.editTextAmount.setText("100")
            binding.editTextAmount.setSelection(binding.editTextAmount.length())
        }
        binding.button500.setOnClickListener {
            binding.editTextAmount.isFocusable = false
            binding.button500.isChecked = true
            binding.button999.isChecked = false
            binding.button100.isChecked = false
            binding.editTextAmount.setText("500")
            binding.editTextAmount.setSelection(binding.editTextAmount.length())
        }

        binding.button999.setOnClickListener {
            binding.button500.isChecked = false
            binding.button999.isChecked = true
            binding.button100.isChecked = false
            binding.editTextAmount.setText("999")
            binding.editTextAmount.setSelection(binding.editTextAmount.length())
            binding.editTextAmount.isFocusable = false
        }

        binding.textViewPromocode.setSafeOnClickListener {
            val intent = Intent(context, PromoCodeActivity::class.java)
            launcher.launch(intent)
        }

        binding.textViewRemovePromocode.setSafeOnClickListener {
            appliedPromocode = null
            binding.appliedPromoCodeCL.gone()
            binding.textViewPromocode.visible()
        }

        binding.buttonAddMoney.setSafeOnClickListener {
            validations()
        }


        /*binding.editTextAmount.doOnTextChanged { text, start, before, count ->
            if (binding.editTextAmount.hasFocus() && isTextEdited){
                binding.button500.isChecked = false
                binding.button999.isChecked = false
                binding.button100.isChecked = false
            }
        }*/

        binding.editTextAmount.doAfterTextChanged {
            if (it.toString().length == 1 && it.toString().startsWith("0")) {
                it!!.clear()
            }
        }

      /*  binding.editTextAmount.setOnFocusChangeListener { view, boolean ->
            isTextEdited = false
        }*/

        return binding.root
    }

    private fun validations() {
        addMoney = binding.editTextAmount.text.toString().trim()
        if (addMoney.isBlank()) {
            requireContext().showToastError("Please enter money first to continue!!")
        } else {
            val addMoneyRequest = AddMoneyRequest()
            if (appliedPromocode == null) {
                addMoneyRequest.amount = addMoney
                addMoneyRequest.promocodeId = ""
                addMoneyRequest.paymentType = ""
            } else {
                addMoneyRequest.amount = addMoney
                addMoneyRequest.promocodeId = appliedPromocode!!.promocodeId.toString()
                addMoneyRequest.paymentType = ""
            }
            onSelected(addMoneyRequest, bottomSheet!!)
        }
    }

}
