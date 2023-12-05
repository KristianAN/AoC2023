{
  description = "AoC";

  inputs = {
    nixpkgs.url = github:nixos/nixpkgs/nixpkgs-unstable;
    flake-utils.url = github:numtide/flake-utils;
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [
            (f: p: {
              sbt = p.sbt.override { jre = p.graalvm-ce; };
              scala-cli = p.scala-cli.override { jre = p.graalvm-ce; };
            })
          ];
        };
        jdk = pkgs.graalvm-ce;

        buildRelease = pkgs.writeShellScriptBin "build-release" ''
          scala-cli --power package . --native -o aoc -f --native-mode release-full
        '';

        jvmInputs = with pkgs; [
          buildRelease
          stdenv
          boehmgc
          libunwind
          clang
          zlib
          jdk
          scalafmt
          scala-cli
          haskellPackages.cabal-install
          haskellPackages.cabal-fmt
          haskellPackages.hlint
          haskellPackages.ghc
          haskellPackages.haskell-language-server
        ];

        
        jvmHook = ''
          JAVA_HOME="${jdk}"
        '';

      in
      {

        LLVM_BIN = pkgs.clang + "/bin";
        devShells.default = pkgs.mkShell {
          name = "aoc-dev-shell";
          buildInputs = jvmInputs;
          shellHook = jvmHook;
        };
      }
    );

}

