@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    public List<Friend> getFriendsByUserId(Integer userId) {
        return friendRepository.findByUserId(userId);
    }
}
