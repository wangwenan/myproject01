package com.newland.beecode.web;

import com.intensoft.base.Dictionary;
import com.intensoft.formater.DictViewFormatter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.newland.beecode.domain.MarketingAct;
import com.newland.beecode.domain.MarketingCatalog;
import com.newland.beecode.domain.Partner;
import com.newland.beecode.domain.PartnerCatalog;
import com.newland.beecode.domain.condition.MarketingActCondition;
import com.newland.beecode.domain.condition.QueryResult;
import com.newland.beecode.domain.report.CouponSumaryReport;
import com.newland.beecode.domain.report.MarketingActSummary;
import com.newland.beecode.exception.AppException;
import com.newland.beecode.exception.ErrorsCode;
import com.newland.beecode.service.CouponService;
import com.newland.beecode.service.MarketingActService;
import com.newland.beecode.service.MarketingCatalogService;
import com.newland.beecode.service.PartnerCatalogService;
import com.newland.beecode.service.PartnerService;
import com.newland.utils.PaginationHelper;
import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/marketingacts")
@Controller
public class MarketingActController extends BaseController{
    
    @Resource(name = "dictViewService")
    private DictViewFormatter dictView;
    
	@Autowired
	private MarketingActService marketingActService;
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private PartnerCatalogService partnerCatalogService;
	@Autowired
	private MarketingCatalogService marketingCatalogService;
	
	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String append(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "actNo", required = true) Long actNo,
			Model model, HttpServletRequest request) {

		try {
			marketingActService.append(actNo, file);
		} catch (AppException e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/marketingacts/find/append";
	}

	@RequestMapping(value = "/append/{actNo}", method = RequestMethod.GET)
	public String appendForm(@PathVariable("actNo") Long actNo, Model model) {
		try {
			addDateTimeFormatPatterns(model);
			MarketingAct marketingAct= marketingActService.findByActNo(actNo);
			model.addAttribute("marketingAct",marketingAct);
			if(marketingAct.getBizNo().equals("00")){
				model.addAttribute("fuck", true);
			}
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/append/form";
	}

	@RequestMapping(value = "/find/append", method = RequestMethod.GET)
	public String findMarketingAct4Append(@RequestParam("bizNo") String bizNo,
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minEndDate,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxEndDate,
			@RequestParam("actName") String actName,
			@RequestParam("actStatus") Integer actStatus, Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request) {
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), request.getQueryString());
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			MarketingActCondition mac=new MarketingActCondition();
			mac.setBizNo(bizNo);
			mac.setStartGenDate(minEndDate);
			mac.setEndGenDate(maxEndDate);
			mac.setActName(actName);
			mac.setActStatus(actStatus);
			//mac.setPage(page);
			//mac.setSize(size);
			//mac.setPagination(true);
			List<MarketingAct> marketingActs=this.marketingActService.findByCondition(mac,(page-1)*size,size);
			int maxPages = PaginationHelper.calcMaxPages(size, this.marketingActService.countByCondition(mac));
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			model.addAttribute("marketingacts",marketingActs);
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/list/append";
	}
	@RequestMapping(value = "/find/append",params = { "find=ByCondition" ,"form"}, method = RequestMethod.GET)
	public String findMarketingActAppendForm(Model model) {
		addDateTimeFormatPatterns(model);
		return "marketingacts/list/append";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String create(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@Valid MarketingAct marketingAct, BindingResult result,
			@RequestParam("partners") Long[] partners,
			Model model, HttpServletRequest request) throws IOException {
		System.out.println("-1111111111------------------------->");
		System.out.println(partners);
		try {
			marketingActService.createMarketingAct(marketingAct,partners, file);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:/marketingacts/"
				+ encodeUrlPathSegment(marketingAct.getActNo().toString(),
						request);
	}

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		try {
			model.addAttribute("marketingAct", new MarketingAct());
			model.addAttribute("checkCards", dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/create";
	}

	@RequestMapping(value = "/{actNo}", method = RequestMethod.GET)
	public String show(@PathVariable("actNo") Long actNo, Model model) {
		try {
			addDateTimeFormatPatterns(model);
			MarketingAct act = this.marketingActService.findByActNo(actNo);
			if (act.getActStatus() > MarketingAct.STATUS_BEFORE_GIVE) {
				MarketingActSummary marketingSummary = this.marketingActService.marketingSummary(actNo);
				act.setSummary(marketingSummary);
				model.addAttribute("statistics",true);
			}
			model.addAttribute("marketingact", act);
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/show";
	}
	@RequestMapping(value="/{actNo}",params = "form",method=RequestMethod.GET)
	public String update(@PathVariable("actNo") Long actNo, Model model){	
		try {
			MarketingAct act = this.marketingActService.findByActNo(actNo);
			model.addAttribute("marketingAct", act);
			model.addAttribute("checkCards", dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME_CHECK_CARD));
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			return "prompt";
		}
		return "marketingacts/update";
	}
	@RequestMapping(method=RequestMethod.PUT)
	public String updateSubmit( MarketingAct marketingAct,
			@RequestParam("partnerIds") Long[] partners,
			Model model,
			HttpServletRequest request){
		System.out.println(marketingAct.getActName());
		System.out.println(marketingAct.getBindCard());
		try{
			this.marketingActService.update(marketingAct,partners);
		}catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "redirect:marketingacts/"+marketingAct.getActNo();
	}
	@RequestMapping(value = "{actNo}/detail", method = RequestMethod.GET)
	public String couponDetail(@PathVariable("actNo") Long actNo, Model model){
		try {
			CouponSumaryReport rpt = couponService.summaryByMarketing(actNo);
			model.addAttribute("rpt", rpt);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/report";
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model model) {
		try {
			if (page != null || size != null) {
				int sizeNo = size == null ? 10 : size.intValue();
				model.addAttribute("marketingacts", this.marketingActService.findMarketingActEntries(
								page == null ? 0 : (page.intValue() - 1) * sizeNo,
								sizeNo));
				float nrOfPages = (float) this.marketingActService.countMarketingActs()
						/ sizeNo;
				model.addAttribute(
						"maxPages",
						(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
								: nrOfPages));
			} else {
				model.addAttribute("marketingacts",
						
			                        this.marketingActService.findAll());
			}
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/list";
	}

	/**
	 * 作废指令，仅在审核状态的营销活动可以作废
	 * 
	 * @param actNo
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/invalid/{actNo}", method = RequestMethod.POST)
	public String invalid(@PathVariable("actNo") Long actNo,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			Model model,HttpServletRequest request)  {
		try {
			marketingActService.invalidMarketingAct(actNo);
			model.addAttribute("page", (page == null) ? "1" : page.toString());
			model.addAttribute("size", (size == null) ? "10" : size.toString());
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		
		return "redirect:/marketingacts?"+query;
	}
    
	@RequestMapping(value = "/find/check", method = RequestMethod.GET)
	public String findMarketingAct4Check(Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		        try {
					Map<String, String> queryParams = PaginationHelper.makeParameters(
					request.getParameterMap(), "");
					page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
					size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
					String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
					QueryResult qr=this.marketingActService.findMarketingActEntriesByActStatus(MarketingAct.STATUS_BEFORE_RECHECK, (page-1)*size, size);
					model.addAttribute("marketingacts",qr.getResultList());
                    int maxPages = PaginationHelper.calcMaxPages(size, qr.getCount());
                    model.addAttribute("maxPages",maxPages);
                    model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
                    model.addAttribute(PaginationHelper.PARAM_PAGE, page);
                    model.addAttribute(PaginationHelper.PARAM_SIZE, size);
				} catch (NumberFormatException e) {
					model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
					this.logger.error(this.getMessage(e), e);
					return "prompt";
				}
		return "marketingacts/list/check";
	}

	@RequestMapping(value = "/check/{actNo}", method = RequestMethod.GET)
	public String checkMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		try {
			addDateTimeFormatPatterns(model);
			model.addAttribute("marketingAct",this.marketingActService.findByActNo(actNo));
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/check/form";
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkMarketingAct(
			@RequestParam(value = "actNo", required = true) Long actNo,
			@RequestParam(value = "actStatus", required = true) Integer actStatus,
			Model model) {
		try {
			model.addAttribute("count", marketingActService.checkMarketingAct(actNo, actStatus));		
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/checkTip";
	}
	@RequestMapping(value = "/find/send", method = RequestMethod.GET)
	public String findMarketingActSend(Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "query", required = false) String query,
			HttpServletRequest request) {
		        try {
					Map<String, String> queryParams = PaginationHelper.makeParameters(
					request.getParameterMap(), "");
					page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
					size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
					String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
					QueryResult qr=this.marketingActService.findMarketingActEntriesByActStatus(MarketingAct.STATUS_BEFORE_GIVE, (page-1)*size, size);
                    model.addAttribute("marketingacts",qr.getResultList());
                    int maxPages = PaginationHelper.calcMaxPages(size, qr.getCount());
                    model.addAttribute("maxPages",maxPages);
                    model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
                    model.addAttribute(PaginationHelper.PARAM_PAGE, page);
                    model.addAttribute(PaginationHelper.PARAM_SIZE, size);
				} catch (NumberFormatException e) {
					model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
					this.logger.error(this.getMessage(e), e);
					return "prompt";
				}
		return "marketingacts/list/send";
	}
	@RequestMapping(value = "/send/{actNo}", method = RequestMethod.GET)
	public String sendMarketingActForm(@PathVariable("actNo") Long actNo,
			Model model) {
		try {
			addDateTimeFormatPatterns(model);
			model.addAttribute("marketingact", this.marketingActService.findByActNo(actNo));
			model.addAttribute("itemId", actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/send/form";
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String send(Model model,@RequestParam(value = "actNo", required = true) Long actNo){
		try {
			this.marketingActService.marketingActSend(actNo);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/sendTip";
	}
	/**
	 * 组合条件查询，至少输入一个条
	 * 
	 * @param minGenTime
	 * @param maxGenTime
	 * @param businessType
	 * @param minEndDate
	 * @param maxEndDate
	 * @param actName
	 * @param actStatus
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = { "find=ByCondition" }, method=RequestMethod.GET)
	public String findMarketingActsByCondition(
			@RequestParam("bizNo") String bizNo,
			@RequestParam("minEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date minEndDate,
			@RequestParam("maxEndDate") @org.springframework.format.annotation.DateTimeFormat(style = "M-") Date maxEndDate,
			@RequestParam("actStatus") Integer actStatus, 
			@RequestParam("marketingCatalog") String marketingCatalog,
			@RequestParam("actNo") String actNo,Model model,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			HttpServletRequest request) {
		try {
			Map<String, String> queryParams = PaginationHelper.makeParameters(
			request.getParameterMap(), request.getQueryString());
			page = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_PAGE));
			size = Integer.valueOf(queryParams.get(PaginationHelper.PARAM_SIZE));
			String queryStr = queryParams.get(PaginationHelper.PARAM_QUERY_STRING);
			MarketingActCondition mac=new MarketingActCondition();
			mac.setMarketingCatalogId(new Long(marketingCatalog)); 
			mac.setBizNo(bizNo);
			mac.setStartGenDate(minEndDate);
			mac.setEndGenDate(maxEndDate);
			mac.setActStatus(actStatus);
			List<MarketingAct> marketingActs=this.marketingActService.findByCondition(mac,(page-1)*size,size);
			int maxPages = PaginationHelper.calcMaxPages(size, this.marketingActService.countByCondition(mac));
			model.addAttribute(PaginationHelper.PARAM_QUERY_STRING, queryStr);
			model.addAttribute("maxPages",maxPages);
			model.addAttribute(PaginationHelper.PARAM_PAGE, page);
			model.addAttribute(PaginationHelper.PARAM_SIZE, size);
			model.addAttribute("marketingacts",marketingActs);
			addDateTimeFormatPatterns(model);
		} catch (Exception e) {
			model.addAttribute(ErrorsCode.MESSAGE, this.getMessage(e));
			this.logger.error(this.getMessage(e), e);
			return "prompt";
		}
		return "marketingacts/findMarketingActsByCondition";
	}

	@RequestMapping(params = { "find=ByCondition", "form" }, method = RequestMethod.GET)
	public String findMarketingActsByConditionForm(Model model) {
		addDateTimeFormatPatterns(model);
		
		return "marketingacts/findMarketingActsByCondition";
	}
	
	@ModelAttribute("partners")
	public Collection<Partner> populatePartners() {
		return this.partnerService.findAll();
	}
	@ModelAttribute("partnerCatalogs")
	public Collection<PartnerCatalog> populatePartnerCatalogs() {
		return this.partnerCatalogService.findAll();
	}
	@ModelAttribute("marketingCatalogs")
	public Collection<MarketingCatalog> populateMarketingCatalogs() {
		return this.marketingCatalogService.findAll();
	}
	@ModelAttribute("marketingsBlank")
	public Collection<MarketingAct> populateMarketingsBlank() {
		return new ArrayList<MarketingAct>();
	}
	@ModelAttribute("bizTypes")
	public Collection<Dictionary> populateBizTypes() {
            return dictView.getSelectModelCollection(MarketingAct.BUSINESS_TYPE);
	}
	@ModelAttribute("marketingstatusList")
	public Collection<Dictionary> populateMarketingstatusList() {
            return dictView.getSelectModelCollection(MarketingAct.DICT_KEY_NAME);
	}

}
