import {Injectable} from '@angular/core';
import {RxStompService, StompState} from "@stomp/ng2-stompjs";
import {Subscription} from "rxjs";

type StompCallback<T> = (response: T) => void

@Injectable({
    providedIn: 'root'
})
export class StompService {

    subscriptionList: Array<Subscription> = [];

    constructor(private rxStompService: RxStompService) {
        this.rxStompService.connectionState$
            .asObservable()
            .subscribe((status: string) => {
                console.log(`Stomp connection status: ${StompState[status]}`);
                if (StompState[status] === StompState.DISCONNECTING) {
                    this.subscriptionList.forEach(value => value.unsubscribe())
                }
            });
    }

    publish(destination: string, body: any) {
        console.log("StompService :" + destination + ":", body)

        this.rxStompService.stompClient.publish({destination, body: body})
    }

    subscribePersonal<T>(destination: string, token: string, callback: StompCallback<T>) {
        const subscription = this.rxStompService.connected$
            .subscribe(value => {
                console.log("subscribePersonal " + `${destination}@${token}`);
                return this.rxStompService.stompClient.subscribe(`${destination}@${token}`, message => {
                    let object;
                    try {
                        object = JSON.parse(message.body);
                    } catch (e) {
                        object = message.body
                    }
                    callback(object);
                })
            })
        this.subscriptionList.push(subscription);
        return subscription;
    }

    subscribeGlobal<T>(destination: string, callback: StompCallback<T>) {
        const subscription = this.rxStompService.connected$
            .subscribe(value => {
                console.log("subscribeGlobal " + `${destination}`);
                return this.rxStompService.stompClient.subscribe(destination, message => {
                    let object;
                    try {
                        object = JSON.parse(message.body);
                    } catch (e) {
                        object = message.body
                    }
                    callback(object);
                })
            })
        this.subscriptionList.push(subscription);
        return subscription;
    }
}
