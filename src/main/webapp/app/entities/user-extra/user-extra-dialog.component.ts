import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { UserExtra } from './user-extra.model';
import { UserExtraPopupService } from './user-extra-popup.service';
import { UserExtraService } from './user-extra.service';
import { Biere, BiereService } from '../biere';

@Component({
    selector: 'jhi-user-extra-dialog',
    templateUrl: './user-extra-dialog.component.html'
})
export class UserExtraDialogComponent implements OnInit {

    userExtra: UserExtra;
    authorities: any[];
    isSaving: boolean;

    bierefavorites: Biere[];

    userextras: UserExtra[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private userExtraService: UserExtraService,
        private biereService: BiereService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.biereService.query({filter: 'usersfavs-is-null'}).subscribe((res: Response) => {
            if (!this.userExtra.biereFavorite || !this.userExtra.biereFavorite.id) {
                this.bierefavorites = res.json();
            } else {
                this.biereService.find(this.userExtra.biereFavorite.id).subscribe((subRes: Biere) => {
                    this.bierefavorites = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.userExtraService.query().subscribe(
            (res: Response) => { this.userextras = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userExtra.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userExtraService.update(this.userExtra));
        } else {
            this.subscribeToSaveResponse(
                this.userExtraService.create(this.userExtra));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserExtra>) {
        result.subscribe((res: UserExtra) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: UserExtra) {
        this.eventManager.broadcast({ name: 'userExtraListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackBiereById(index: number, item: Biere) {
        return item.id;
    }

    trackUserExtraById(index: number, item: UserExtra) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-user-extra-popup',
    template: ''
})
export class UserExtraPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userExtraPopupService: UserExtraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userExtraPopupService
                    .open(UserExtraDialogComponent, params['id']);
            } else {
                this.modalRef = this.userExtraPopupService
                    .open(UserExtraDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
