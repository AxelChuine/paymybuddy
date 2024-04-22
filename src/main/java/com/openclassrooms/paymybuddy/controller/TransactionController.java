@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private final ITransactionService service;

    public TransactionController(ITransactionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/new-transaction", method = RequestMethod.GET)
    public String newTransaction() {
        return "/transaction/new-transaction";
    }