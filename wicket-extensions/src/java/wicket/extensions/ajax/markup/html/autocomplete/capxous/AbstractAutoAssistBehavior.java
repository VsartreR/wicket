package wicket.extensions.ajax.markup.html.autocomplete.capxous;

import wicket.RequestCycle;
import wicket.Response;
import wicket.behavior.AbstractAjaxBehavior;
import wicket.markup.html.PackageResourceReference;

/**
 * This is a low level implementation of a bridge between wicket and the
 * autoassist javascript library. The autoassist libarary can be found here:
 * http://capxous.com/autoassist/
 * <p>
 * Usually this class is not used directly, but instead one of its subclasses is
 * used. Direct implementation of this class should only be used when total
 * control is required.
 * 
 * @since 1.2
 * 
 * @author Igor Vaynberg (ivaynberg)
 */
public abstract class AbstractAutoAssistBehavior extends AbstractAjaxBehavior
{
	private final PackageResourceReference AUTOASSIST_JS = new PackageResourceReference(
			AbstractAutoAssistBehavior.class, "autoassist.js");
	private final PackageResourceReference PROTOTYPE_JS = new PackageResourceReference(
			AbstractAutoAssistBehavior.class, "prototype.js");
	private final PackageResourceReference AUTOASSIST_HELPER_JS = new PackageResourceReference(
			AbstractAutoAssistBehavior.class, "autoassist_helper.js");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String getImplementationId()
	{
		return "capxous";
	}

	protected void onBind()
	{
		getComponent().setOutputMarkupId(true);
	}

	protected void onRenderHeadInitContribution(Response response)
	{
		writeJsReference(response, PROTOTYPE_JS);
		writeJsReference(response, AUTOASSIST_JS);
		writeJsReference(response, AUTOASSIST_HELPER_JS);
	}

	protected void onRenderHeadContribution(Response response)
	{
		final String id = getComponent().getMarkupId();
		response.write("<script>registerAutoassist(\"" + id + "\", \"" + getCallbackUrl()
				+ "\");</script>");
	}

	/**
	 * @see wicket.behavior.IBehaviorListener#onRequest()
	 */
	public final void onRequest()
	{
		final RequestCycle requestCycle = RequestCycle.get();
		final String val = requestCycle.getRequest().getParameter("val");
		onRequest(val, requestCycle);
	}

	/**
	 * Callback for the ajax event generated by the autoassist library. This is
	 * where we need to generate our response. For details on the response
	 * contents see the autoassist documentation. Subclasses of this class might
	 * provider a better abstraction from the autoassist specific details.
	 * 
	 * @param input
	 *            the input entered so far
	 * @param requestCycle
	 *            current request cycle
	 */
	protected abstract void onRequest(String input, RequestCycle requestCycle);

}
