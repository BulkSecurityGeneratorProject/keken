import { Biere } from '../biere';
import { Commentaire } from '../commentaire';
export class UserExtra {
    constructor(
        public id?: number,
        public image?: string,
        public biereFavorite?: Biere,
        public bieresLikes?: Biere,
        public amis?: UserExtra,
        public commentaires?: Commentaire,
        public user?: UserExtra,
    ) {
    }
}
