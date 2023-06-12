package io.swagger.api.security;

public class JwtTokenProviderTest {
/*
    @Mock
    private SecretKeyProvider secretKeyProvider;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private final long validityInMilliseconds = 3600000; // 1 hour

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(secretKeyProvider.getSecretKey()).thenReturn("test-secret-key");
        jwtTokenProvider.setValidityInMilliseconds(validityInMilliseconds);
    }

    @Test
    public void testCreateToken() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));

        // Act
        String token = jwtTokenProvider.createToken(userId, roles);

        // Assert
        Claims claims = Jwts.parser().setSigningKey("test-secret-key").parseClaimsJws(token).getBody();
        Assertions.assertEquals(userId.toString(), claims.getSubject());
        Assertions.assertEquals(roles.size(), claims.get("auth", List.class).size());
        Assertions.assertEquals(SimpleGrantedAuthority.class, claims.get("auth", List.class).get(0).getClass());
    }

    @Test
    public void testCreateTokenWithEmptyRoles() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Role> roles = new ArrayList<>();

        // Act
        String token = jwtTokenProvider.createToken(userId, roles);

        // Assert
        Claims claims = Jwts.parser().setSigningKey("test-secret-key").parseClaimsJws(token).getBody();
        Assertions.assertEquals(userId.toString(), claims.getSubject());
        Assertions.assertNull(claims.get("auth"));
    }

    @Test
    public void testCreateTokenWithNullUserId() {
        // Arrange
        UUID userId = null;
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jwtTokenProvider.createToken(userId, roles);
        });
    }

    @Test
    public void testCreateTokenWithNullRoles() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<Role> roles = null;

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jwtTokenProvider.createToken(userId, roles);
        });
    }*/
}